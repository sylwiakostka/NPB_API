package Profile_model;

import java.util.List;

public class Profile {


    private String id;

    private String name;

    private String startHour;

    private String endHour;

    private String startMinute;

    private String endMinute;

    private String maxOrders;

    private String maxTotalAmountGr;

    private String warnAmountGr;

    private String maxTariffGr;

    private String orderDays;

    private String commentForCC;

    private List<Object> filters = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(String startMinute) {
        this.startMinute = startMinute;
    }

    public String getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(String endMinute) {
        this.endMinute = endMinute;
    }

    public String getMaxOrders() {
        return maxOrders;
    }

    public void setMaxOrders(String maxOrders) {
        this.maxOrders = maxOrders;
    }

    public String getMaxTotalAmountGr() {
        return maxTotalAmountGr;
    }

    public void setMaxTotalAmountGr(String maxTotalAmountGr) {
        this.maxTotalAmountGr = maxTotalAmountGr;
    }

    public String getWarnAmountGr() {
        return warnAmountGr;
    }

    public void setWarnAmountGr(String warnAmountGr) {
        this.warnAmountGr = warnAmountGr;
    }

    public String getMaxTariffGr() {
        return maxTariffGr;
    }

    public void setMaxTariffGr(String maxTariffGr) {
        this.maxTariffGr = maxTariffGr;
    }

    public String getOrderDays() {
        return orderDays;
    }

    public void setOrderDays(String orderDays) {
        this.orderDays = orderDays;
    }

    public String getCommentForCC() {
        return commentForCC;
    }

    public void setCommentForCC(String commentForCC) {
        this.commentForCC = commentForCC;
    }

    public List<Object> getFilters() {
        return filters;
    }

    public void setFilters(List<Object> filters) {
        this.filters = filters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
