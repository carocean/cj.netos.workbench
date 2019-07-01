package cj.netos.workbench.program.stub;

import java.util.List;

import cj.netos.workbench.args.Display;
import cj.netos.workbench.args.Style;
import cj.netos.workbench.args.Theme;
import cj.netos.workbench.bs.IThemeBS;
import cj.netos.workbench.stub.IThemeStub;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.gateway.stub.GatewayAppSiteRestStub;

@CjService(name = "/theme.service")
public class ThemeStub extends GatewayAppSiteRestStub implements IThemeStub {
	@CjServiceRef(refByName = "WBEngine.themeBS")
	IThemeBS themeBS;

	@Override
	public void addTheme(String themeid, String name, String creator, String desc) {
		themeBS.addTheme(themeid, name, creator, desc);
	}

	@Override
	public void removeTheme(String themeid) {
		themeBS.removeTheme(themeid);
	}

	@Override
	public List<Theme> pageTheme(long currPage, int pageSize) {
		return themeBS.pageTheme(currPage, pageSize);
	}

	@Override
	public Theme getTheme(String themeid) {
		return themeBS.getTheme(themeid);
	}

	@Override
	public void addDisplay(String displayid, String themeid, String name, String desc) {
		themeBS.addDisplay(displayid, themeid, name, desc);
	}

	@Override
	public void removeDisplay(String themeid, String displayid) {
		themeBS.removeDisplay(themeid, displayid);
	}

	@Override
	public List<Display> listDisplay(String themeid) {
		return themeBS.listDisplay(themeid);
	}

	@Override
	public void addStyle(String styleid, String themeid, String name) {
		themeBS.addStyle(styleid, themeid, name);
	}

	@Override
	public Style getDefaultStyle(String themeid) {
		return themeBS.getDefaultStyle(themeid);
	}

	@Override
	public void emptyStyle(String themeid) {
		themeBS.emptyStyle(themeid);
	}

	@Override
	public void removeStyle(String styleid, String themeid) {
		themeBS.removeStyle(styleid, themeid);
	}

	@Override
	public void setDefaultStyle(String styleid, String themeid) {
		themeBS.setDefaultStyle(styleid, themeid);
	}

	@Override
	public String[] enumStyle(String themeid) {
		return themeBS.enumStyle(themeid);
	}

	@Override
	public Style getStyle(String styleid, String themeid) {
		return themeBS.getStyle(styleid, themeid);
	}

	@Override
	public boolean existsStyle(String styleid, String themeid) {
		return themeBS.existsStyle(styleid, themeid);
	}
	@Override
	public Display getDisplay(String themeid, String displayid) {
		return themeBS.getDisplay(themeid, displayid);
	}
}
