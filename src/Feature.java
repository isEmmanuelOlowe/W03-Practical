/**
* This is a class allows each object in the CSV file to be converted into a object.
*
* @author 180003815
*/

public class Feature {

  private int number;
  private String name;
  private String city;
  private String[] style;
  private int ranking;
  private double rating;
  private String priceRange;
  private int reviewNo;
  private String[] reviews;
  private String urlTA;
  private String idTA;


  /**
  * Creates a Feature in dataset from all the data about the resaurant.
  *
  * @param number the number of the restraunt in dataset
  * @param name the name of the restaurant
  * @param city the city the restaurant id located in
  * @param style the different cuisine style the restaurant serves
  * @param ranking the ranking of the restaurant in its city
  * @param rating the average rating of restaurant by customers
  * @param priceRange the price range of the restaurant
  * @param reviewNo the number of reviews the restaurant has
  * @param reviews written reviews of the restaurant
  * @param urlTA relative trip advisor url of the restaurant
  * @param idTA the trip advisor of the restaurant
  */
  public Feature(int number, String name, String city, String[] style, int ranking, double rating, String priceRange, int reviewNo, String[] reviews, String urlTA, String idTA) {

    this.number = number;
    this.name = name;
    this.city = city;
    this.style = style;
    this.ranking = ranking;
    this.rating = rating;
    this.priceRange = priceRange;
    this.reviewNo = reviewNo;
    this.reviews = reviews;
    this.urlTA = urlTA;
    this.idTA = idTA;
  }

  /**
  * Getter for number label.
  *
  * @return the number of the restaurant
  */
  public int getNumber() {

    return this.number;
  }

  /**
  * Getter for name label.
  *
  * @return the name of the restaurant
  */
  public String getName() {

    return this.name;
  }

  /**
  * Getter for city label.
  *
  * @return the city in which the restaurant is located
  */
  public String getCity() {

    return this.city;
  }

  /**
  * Getter for style label.
  *
  * @return the cuisine styles of the restraunt
  */
  public String[] getStyle() {

    return this.style;
  }

  /**
  * Getter for ranking label.
  *
  * @return the ranking of the resaurant in its city
  */
  public int getRanking() {

    return this.ranking;
  }

  /**
  * Getter for rating label.
  *
  * @return the average customer rating of the restaurant
  */
  public double getRating() {

    return this.rating;
  }

  /**
  * Getter for price range label.
  *
  * @return the price range of the restraunt
  */
  public String getPriceRange() {

    return this.priceRange;
  }

  /**
  * Getter for Number of Reviews label.
  *
  * @return the number of reviews the restaurant has
  */
  public int getReviewNo() {

    return this.reviewNo;
  }

  /**
  * Getter for Reviews label.
  *
  * @return written reviews of the restaurant
  */
  public String[] getReviews() {

    return this.reviews;
  }

  /**
  * Getter for Url Trip Advisor Label.
  *
  * @return the relative Trip Advisor url of the restaurant
  */
  public String getUrlTA() {

    return this.urlTA;
  }

  /**
  * Getter for ID Trip Advisor Label.
  *
  * @return the id of the restaurant on Trip Advisor
  */
  public String getIdTA() {

    return this.idTA;
  }
}
