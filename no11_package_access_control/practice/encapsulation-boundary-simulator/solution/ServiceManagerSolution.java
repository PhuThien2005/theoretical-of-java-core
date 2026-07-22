package services;

/**
 * Reference solution for ServiceManagerSolution.
 */
public class ServiceManagerSolution {

    public String executeTask() {
        InternalWorkerSolution worker = new InternalWorkerSolution();
        return worker.doWork();
    }
}
