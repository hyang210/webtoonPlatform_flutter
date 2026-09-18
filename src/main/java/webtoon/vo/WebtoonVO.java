package webtoon.vo;

/**
 * 웹툰 데이터를 저장하고 전달하는 객체 (Value Object)
 */
public class WebtoonVO {
    private int webtoonId;
    private String koTitle; // 다국어 처리용 한국어 제목
    private String enTitle; // 다국어 처리용 영어 제목
    private String platform;
    private String author;
    private double rating;
    private String genre;
    private String thumbnailPath; // 업로드된 파일의 서버 경로
    private int price;

    public WebtoonVO() {}

	public int getWebtoonId() {
		return webtoonId;
	}

	public void setWebtoonId(int webtoonId) {
		this.webtoonId = webtoonId;
	}

	public String getKoTitle() {
		return koTitle;
	}

	public void setKoTitle(String koTitle) {
		this.koTitle = koTitle;
	}

	public String getEnTitle() {
		return enTitle;
	}

	public void setEnTitle(String enTitle) {
		this.enTitle = enTitle;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}