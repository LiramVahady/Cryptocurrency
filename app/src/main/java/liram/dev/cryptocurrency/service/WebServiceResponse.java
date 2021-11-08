package liram.dev.cryptocurrency.service;

public interface WebServiceResponse {
    <T> void success(T response);
//    <T> void success(List<T> response);
    void error(String message);
    void failure(String message);
}
