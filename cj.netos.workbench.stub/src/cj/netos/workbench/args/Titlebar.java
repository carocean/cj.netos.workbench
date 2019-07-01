package cj.netos.workbench.args;

import java.util.ArrayList;
import java.util.List;

public class Titlebar {
	String title;
	boolean visible;
	String left;
	String right;
	List<MenuItem> menu;
	public Titlebar() {
		menu=new ArrayList<MenuItem>();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	public List<MenuItem> getMenu() {
		return menu;
	}
	
}
