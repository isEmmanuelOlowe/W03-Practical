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

  public Feature(int number, String name, String city, String[] style, int ranking, double rating, String priceRange, int reviewNo, String[] reviews, String urlTA, String idTA){
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

  //getters and setters for variables to ensure they are not changed during runtime
  public int getNumber(){
    return this.number;
  }
  public String getName(){
    return this.name;
  }
  public String getCity(){
    return this.city;
  }
  public String[] getStyle(){
    return this.style;
  }
  public int getRanking(){
    return this.ranking;
  }
  public double getRating(){
    return this.rating;
  }
  public String getPriceRange(){
    return this.priceRange;
  }
  public int getReviewNo(){
    return this.reviewNo;
  }
  public String[] getReviews(){
    return this.reviews;
  }
  public String getUrlTA(){
    return this.urlTA;
  }
  public String getIdTA(){
    return this.idTA;
  }
}
