package Retrofit;

public class ErrorResponse {
    private String state;
    private String msg;
    private String env;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
