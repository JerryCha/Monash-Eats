public class RateController {
    private RateList rateList;
    private OrderList orderList;

    public RateController() {
        rateList = RateList.getInstance();
        orderList = OrderList.getInstance();
    }
}