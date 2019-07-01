package cj.netos.workbench.bs;

import java.util.List;

import cj.netos.workbench.args.Display;
import cj.netos.workbench.args.Style;
import cj.netos.workbench.args.Theme;

public interface IThemeBS {

	void removeTheme(String themeid);

	List<Theme> pageTheme(long currPage, int pageSize);

	Theme getTheme(String themeid);

	void removeDisplay(String themeid, String displayid);

	List<Display> listDisplay(String themeid);

	void emptyStyle(String themeid);

	void addTheme(String themeid, String name, String creator, String desc);

	void addDisplay(String displayid, String themeid, String name, String desc);

	void addStyle(String styleid, String themeid, String name);

	Style getDefaultStyle(String themeid);

	void removeStyle(String styleid, String themeid);

	void setDefaultStyle(String styleid, String themeid);

	String[] enumStyle(String themeid);

	Style getStyle(String styleid, String themeid);

	boolean existsStyle(String styleid, String themeid);

	Display getDisplay(String themeid, String displayid);

}