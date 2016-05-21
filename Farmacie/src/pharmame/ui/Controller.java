package pharmame.ui;

public interface Controller {
	void filterBy(String filterName, String searchKey);
	void printSelected(int[] selectedRows);
	void start();
}
