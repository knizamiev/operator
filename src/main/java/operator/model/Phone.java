package operator.model;

public class Phone {
    private int code;
    private int from;
    private int before;
    private int count;
    private String operator;
    private String region;

    public void setCode(int code) {
        this.code = code;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setBefore(int before) {
        this.before = before;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getCode() {
        return code;
    }

    public int getFrom() {
        return from;
    }

    public int getBefore() {
        return before;
    }

    public int getCount() {
        return count;
    }

    public String getOperator() {
        return operator;
    }

    public String getRegion() {
        return region;
    }
}