#!/usr/bin/env python3
import os
import sys
import subprocess
import shutil
import tempfile
import json
from pathlib import Path

def run_cmd(cmd, cwd=None):
    result = subprocess.run(cmd, capture_output=True, text=True, cwd=cwd)
    return result.returncode, result.stdout, result.stderr

def run_student_test(exercise_path):
    exercise_path = Path(exercise_path)
    src_dir = exercise_path / "src"
    test_dir = exercise_path / "test"

    if not src_dir.exists() or not test_dir.exists():
        return {
            "status": "error",
            "message": f"Missing directories: src={src_dir.exists()}, test={test_dir.exists()}"
        }

    src_files = list(src_dir.glob("*.java"))
    test_files = list(test_dir.glob("*Test.java"))

    if not src_files or not test_files:
        return {
            "status": "error",
            "message": "Missing java files in src or test folders."
        }

    test_runner_file = test_files[0]
    package = ""
    with open(test_runner_file, 'r', encoding='utf-8') as f:
        for line in f:
            if line.strip().startswith("package "):
                package = line.strip().split("package ")[1].rstrip(";").strip()
                break

    class_name = test_runner_file.stem
    full_test_class = f"{package}.{class_name}" if package else class_name

    with tempfile.TemporaryDirectory() as tmpdir:
        # Compile starter + test
        compile_cmd = ["javac", "-d", tmpdir] + [str(f) for f in src_files + test_files]
        ret, stdout, stderr = run_cmd(compile_cmd)
        if ret != 0:
            return {
                "status": "fail",
                "message": "Compilation failed.",
                "log": f"STDOUT:\n{stdout}\nSTDERR:\n{stderr}"
            }

        # Run test
        run_cmd_list = ["java", "-cp", tmpdir, full_test_class]
        ret_val, run_stdout, run_stderr = run_cmd(run_cmd_list)
        if ret_val == 0:
            return {
                "status": "success",
                "message": "All tests passed successfully!",
                "log": run_stdout.strip()
            }
        else:
            return {
                "status": "fail",
                "message": "Some assertions failed.",
                "log": f"STDOUT:\n{run_stdout}\nSTDERR:\n{run_stderr}"
            }

def verify_solution_integrity(exercise_path):
    # This is the repository audit mode
    exercise_path = Path(exercise_path)
    src_dir = exercise_path / "src"
    test_dir = exercise_path / "test"
    sol_dir = exercise_path / "solution"

    if not src_dir.exists() or not test_dir.exists() or not sol_dir.exists():
        return {
            "status": "error",
            "message": "Missing directories for audit."
        }

    src_files = list(src_dir.glob("*.java"))
    test_files = list(test_dir.glob("*Test.java"))
    sol_files = list(sol_dir.glob("*.java"))

    if not src_files or not test_files or not sol_files:
        return {
            "status": "error",
            "message": "Missing java files for audit."
        }

    test_runner_file = test_files[0]
    package = ""
    with open(test_runner_file, 'r', encoding='utf-8') as f:
        for line in f:
            if line.strip().startswith("package "):
                package = line.strip().split("package ")[1].rstrip(";").strip()
                break
    class_name = test_runner_file.stem
    full_test_class = f"{package}.{class_name}" if package else class_name

    with tempfile.TemporaryDirectory() as tmpdir:
        # Step 1: Starter must fail
        starter_out = os.path.join(tmpdir, "starter")
        os.makedirs(starter_out, exist_ok=True)
        ret, stdout, stderr = run_cmd(["javac", "-d", starter_out] + [str(f) for f in src_files + test_files])
        if ret != 0:
            return {"status": "fail", "message": f"Starter compilation failed:\n{stderr}"}
        
        ret_starter, stdout_starter, stderr_starter = run_cmd(["java", "-cp", starter_out, full_test_class])
        if ret_starter == 0:
            return {"status": "fail", "message": "Starter code passed tests but was expected to FAIL."}

        # Step 2: Solution must pass
        # We copy the test files, rename references of ClassName -> ClassNameSolution,
        # and strip 'public' from the test class declaration to avoid filename mismatch errors.
        import re
        solution_test_dir = os.path.join(tmpdir, "sol_tests")
        os.makedirs(solution_test_dir, exist_ok=True)
        sol_test_files = []
        
        rename_map = {}
        for sf in src_files:
            old_c = sf.stem
            if old_c != "module-info":
                rename_map[old_c] = old_c + "Solution"
                
        for tf in test_files:
            content = tf.read_text(encoding='utf-8')
            for old_c, new_c in rename_map.items():
                content = re.sub(r'\b' + re.escape(old_c) + r'\b', new_c, content)
                
            # Strip public from test class definition so it compiles in the original filename
            content = content.replace("public class ", "class ")
            
            temp_tf = Path(solution_test_dir) / tf.name
            temp_tf.write_text(content, encoding='utf-8')
            sol_test_files.append(temp_tf)
            
        solution_out = os.path.join(tmpdir, "solution")
        os.makedirs(solution_out, exist_ok=True)
        ret, stdout, stderr = run_cmd(["javac", "-d", solution_out] + [str(f) for f in sol_files + sol_test_files])
        if ret != 0:
            return {"status": "fail", "message": f"Solution compilation failed:\n{stderr}"}
        
        ret_sol, stdout_sol, stderr_sol = run_cmd(["java", "-cp", solution_out, full_test_class])
        if ret_sol != 0:
            return {"status": "fail", "message": f"Solution failed tests:\n{stderr_sol}"}

    return {"status": "success", "message": "Exercise verified successfully: starter fails, solution passes."}

def main():
    root = Path(__file__).parent.parent.resolve()
    topics = sorted([d for d in root.iterdir() if d.is_dir() and (d.name.startswith("no") or d.name[0].isdigit())])
    
    results = {}
    
    # Parse arguments
    args = sys.argv[1:]
    audit_mode = "--audit" in args
    if audit_mode:
        args.remove("--audit")
        
    target_topic = args[0] if len(args) > 0 else None
    
    for topic in topics:
        if target_topic and topic.name != target_topic:
            continue
            
        practice_dir = topic / "practice"
        if not practice_dir.exists():
            continue
            
        exercises = sorted([d for d in practice_dir.iterdir() if d.is_dir()])
        topic_results = {}
        
        for exercise in exercises:
            if target_topic:
                print(f"\n──────────────────────────────────────────────────")
                print(f"Checking exercise: {topic.name}/{exercise.name}")
                print(f"──────────────────────────────────────────────────")
            else:
                print(f"Verifying {topic.name}/{exercise.name}...")
                
            if audit_mode:
                res = verify_solution_integrity(exercise)
            else:
                res = run_student_test(exercise)
                
            topic_results[exercise.name] = res
            print(f"Result: {res['status'].upper()}")
            
            if not audit_mode:
                if "log" in res:
                    print(res["log"].strip())
            else:
                print(f"Detail: {res.get('message', '')[:120]}")
            
        if topic_results:
            results[topic.name] = topic_results

    # Output report
    report_path = root / "reports" / "practice-audit-report.json"
    report_path.parent.mkdir(exist_ok=True)
    with open(report_path, "w", encoding="utf-8") as f:
        json.dump(results, f, indent=2)
    print(f"\nAudit complete. Report written to {report_path}")

if __name__ == "__main__":
    main()
