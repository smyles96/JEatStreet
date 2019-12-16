package io.github.smyles96.eatstreet.model.order;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import io.github.smyles96.eatstreet.exception.EatStreetApiException;
import io.github.smyles96.eatstreet.model.EatStreetModel;
import io.github.smyles96.eatstreet.model.restaurant.OrderType;
import io.github.smyles96.eatstreet.model.restaurant.Restaurant;
import io.github.smyles96.eatstreet.model.user.Address;
import io.github.smyles96.eatstreet.model.user.CreditCard;
import io.github.smyles96.eatstreet.util.http.ApiEndpoint;
import io.github.smyles96.eatstreet.util.http.EatStreetRequestor;
import io.github.smyles96.eatstreet.util.json.JsonConverter;

/**
 * This model class represents an order on the EatStreet API.
 * It consists of OrderItems, each of which have 0 or more
 * OrderCustomizationChoice objects.
 * 
 * For example:
 *     Order
 *         OrderItem (Cheeseburger)
 *             OrderCustomizationChoice (With Pickles)
 *         OrderItem (French Fries)
 *             OrderCustomizationChoice (Wedge Cut)
 * 
 * Making an order:
 * <pre>{@code
 * try {
 *      // Find a restaurant
 *      List<Restaurant> results = api.findRestaurants("9308 Baker Street, Manassas, VA", OrderType.DELIVERY, 2);
 *      Restaurant target = results.get(0);
 * 
 *      // Create a new order
 *      Order testOrder = new Order(target);
 *
 *      // Set the type of order and the method of payment
 *      testOrder.setMethod(OrderType.PICKUP);
 *      testOrder.setPayment(PaymentMethod.CARD);
 *
 *      // Create a new card to use for the order
 *      CreditCard card = new CreditCard();
 *      card.setCardholderName("John Doe");
 *      card.setCardholderStreetAddress("1234 Example Lane, Blacksburg, VA");
 *      card.setCardholderZip("24060");
 *      card.setCardNumber("1234123412341234");
 *      card.setCvv("123");
 *
 *      // Create a new address for the order
 *      Address address = new Address();
 *      address.setStreetAddress("1234 Example Lane");
 *      address.setCity("Blacksburg");
 *      address.setState("VA");
 *      address.setZip("24060");
 * 
 *      // Create a new OrderItem using the api key of a MenuItem
 *      OrderItem cheeseCake = new OrderItem("12997024", "Cheesecake", 2.99);
 *      cheeseCake.addCustomization(new OrderCustomizationChoice("130587488", "Classic Flavor", 0.00));
 *
 *      OrderItem kabob = new OrderItem("12511258", "Lebanese Chicken Kabob", 8.99);
 *      kabob.addCustomization(new OrderCustomizationChoice("118725177", "French Fries", 1.99));
 *
 *      testOrder.setPhone("123-456-7890"); // Change phone number for order
 *      testOrder.setCard(card);
 *      testOrder.setAddress(address);
 *
 *      testOrder.addItem(cheeseCake);
 *      testOrder.addItem(kabob);
 *
 *      // The registered user's phone number, first name, and last name
 *      // are used when placing an order. These fields can be overridden
 *      // for this single order by setting their fields in the order object
 *      // 
 *      // Only those set will be overriden
 *      // 
 *      // newOrder.setPhone("123-456-7890")
 *      // newOrder.setFirstName("Samuel");
 *      // newOrder.setLastName("Myles")
 * 
 *      System.out.println("\n====TOTALS====");
 *      System.out.println(testOrder.getSubTotal());
 *      System.out.println(testOrder.getTax());
 *      System.out.println(testOrder.getTotal());
 *  
 *      // In order to get the totals for the order, the Restaurant's validateOrder
 *      // method must be called before its sendOrder method. This also validates
 *      // the input of the order
 *      target.validateOrder(testOrder);
 * 
 *      // Send the order for real
 *      target.sendOrder(testOrder);
 * }
 * catch(EatStreetApiException e) {
 *     System.out.println(e.getMessage());
 * }
 * }</pre>{
 * 
 * @author smyles96
 */
public class Order extends EatStreetModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * INSTANCE VARIABLES
     */
    private Integer id;
    private Integer datePlaced;
    private String method;
    private String payment;
    private String restaurantApiKey;
    private String recipientApiKey;
    private CreditCard card;
    private Address address;
    private String comments;
    private Double tip;
    private Double tax;
    
    @SerializedName("subtotal")
    private Double subTotal;
    
    private Double total;
    private List<OrderItem> items;
    
    private transient String firstName;
    private transient String lastName;
    private transient String phone;
    private transient Double restaurantTaxRate;
    
    private transient DecimalFormat df = new DecimalFormat("0.00");

    /*
     * CONSTRUCTOR(S)
     */
    
    public Order() {
        super("");
        
        this.card = new CreditCard();
        this.items = new ArrayList<>();
    }
    
    public Order(String restaurantApiKey, Double restaurantTaxRate) {
        super("");
        
        this.card = new CreditCard();
        this.items = new ArrayList<>();
        
        this.restaurantApiKey = restaurantApiKey;
        this.restaurantTaxRate = restaurantTaxRate;
    }
    
    public Order(Restaurant restaurantToOrderFrom) {
        super("");
        
        this.card = new CreditCard();
        this.items = new ArrayList<>();
        
        this.restaurantApiKey = restaurantToOrderFrom.getApiKey();
        this.restaurantTaxRate = restaurantToOrderFrom.getTaxRate();
    }

    
    /*
     * GETTERS and SETTERS
     */
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(Integer datePlaced) {
        this.datePlaced = datePlaced;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    
    public void setMethod(OrderType type) {
        this.method = type.toString();
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
    
    public void setPayment(PaymentMethod payment) {
        this.payment = payment.toString();
    }

    public String getRestaurantApiKey() {
        return restaurantApiKey;
    }

    public void setRestaurantApiKey(String restaurantApiKey) {
        this.restaurantApiKey = restaurantApiKey;
    }

    public String getRecipientApiKey() {
        return recipientApiKey;
    }

    public void setRecipientApiKey(String recipientApiKey) {
        this.recipientApiKey = recipientApiKey;
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public String getComments() {
        return comments;
    }


    public void setComments(String comments) {
        this.comments = comments;
    }

    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    public Double getSubTotal() {
        this.subTotal = this.getItems()
                            .stream()
                            .collect(Collectors.summingDouble(item -> item.calculateSubtotal()));
        
        // Round up to two decimal places
        df.setRoundingMode(RoundingMode.UP);
        this.subTotal = Double.parseDouble( df.format(this.subTotal) );
        
        return this.subTotal;
    }
    
    public void setSubTotal(Double subtotal) {
        this.subTotal = subtotal;
    }
    
    public Double getTax() {
        this.tax = this.getSubTotal() * this.restaurantTaxRate;
        
        // Round up to two decimal places
        df.setRoundingMode(RoundingMode.UP);
        this.tax = Double.parseDouble( df.format(this.tax) );
        
        return this.tax;
    }


    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        this.total = this.getSubTotal() + this.getTax();
        
        // Round up to two decimal places
        df.setRoundingMode(RoundingMode.UP);
        this.total = Double.parseDouble( df.format(this.total) );
        
        return this.total;
    }


    public void setTotal(Double total) {
        this.total = total;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTotalItems() {
        return getItems().size();
    }
    
    /**
     * Get the status of an order
     * 
     * @return A List of OrderStatus objects indicating the statuses of the
     *         order and the date the status changees occurred
     *         
     * @throws EatStreetApiException If a connection or parsing error occurs
     */
    public List<OrderStatus> getOrderStatus() throws EatStreetApiException {
        try(Reader response = EatStreetRequestor.makeGetRequest(ApiEndpoint.ORDER_STATUS, null, this.getApiKey())) {
            // Parse the response as a JSON object
            JsonArray json = JsonParser.parseReader(response).getAsJsonArray();
            
            // Extract the apiKey JSON property as a Java String 
            return Arrays.asList( JsonConverter.fromJson(json, OrderStatus[].class) );
        }
        catch (IOException e) {
            throw new EatStreetApiException("Unable to close the HTTP response object");
        }
    }
    
    /*
     * INSTANCE METHODS
     */
    
    /**
     * Adds an item to this Order
     * 
     * @param item The item to add
     * 
     * @return true if item was added successfully, false otherwise
     */
    public boolean addItem(OrderItem item) {
        return items.add(item);
    }
    
    /**
     * Adds a list of OrderItems to this Order
     * 
     * @param items The list of OrderItems to add
     * 
     * @return true if added successfully, false otherwise
     */
    public boolean addItems(List<OrderItem> items) {
        return items.addAll(items);
    }
}
