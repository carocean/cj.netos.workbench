package cj.netos.workbench.stub;

import java.util.List;

import cj.netos.workbench.args.Display;
import cj.netos.workbench.args.Style;
import cj.netos.workbench.args.Theme;
import cj.studio.gateway.stub.annotation.CjStubInParameter;
import cj.studio.gateway.stub.annotation.CjStubMethod;
import cj.studio.gateway.stub.annotation.CjStubReturn;
import cj.studio.gateway.stub.annotation.CjStubService;

@CjStubService(bindService = "/theme.service", usage = "主题管理")
public interface IThemeStub {
	@CjStubMethod(usage = "添加主题")
	void addTheme(@CjStubInParameter(key = "themeid", usage = "工作台编码") String themeid,
			@CjStubInParameter(key = "name", usage = "工作台中文名") String name,
			@CjStubInParameter(key = "creator", usage = "工作台创建者") String creator,
			@CjStubInParameter(key = "desc", usage = "注释，如果有。") String desc);

	@CjStubMethod(usage = "移除主题")
	void removeTheme(@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid);

	@CjStubMethod(usage = "列出主题")
	@CjStubReturn(elementType = Theme.class, usage = "主题集合")
	List<Theme> pageTheme(@CjStubInParameter(key = "currPage", usage = "分页") long currPage,
			@CjStubInParameter(key = "pageSize", usage = "页大小") int pageSize);

	@CjStubMethod(usage = "主题分页")
	Theme getTheme(@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid);

	@CjStubMethod(usage = "添加显示器")
	void addDisplay(@CjStubInParameter(key = "displayid", usage = "显示器标识") String displayid,
			@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid,
			@CjStubInParameter(key = "name", usage = "显示器名") String name,
			@CjStubInParameter(key = "desc", usage = "显示器说明") String desc);

	@CjStubMethod(usage = "移除显示器")
	void removeDisplay(@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid,
			@CjStubInParameter(key = "displayid", usage = "显示器编号") String displayid);

	@CjStubMethod(usage = "获取显示器")
	Display getDisplay(@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid,
			@CjStubInParameter(key = "displayid", usage = "显示器编号") String displayid);

	@CjStubMethod(usage = "列出显示器")
	@CjStubReturn(elementType = Display.class, usage = "集合")
	List<Display> listDisplay(@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid);

	@CjStubMethod(usage = "为主题添加样式")
	void addStyle(@CjStubInParameter(key = "styleid", usage = "样式标识") String styleid,
			@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid,
			@CjStubInParameter(key = "name", usage = "样式名") String name);

	@CjStubMethod(usage = "从主题中移除样式")
	void removeStyle(@CjStubInParameter(key = "styleid", usage = "样式标识") String styleid,
			@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid);

	@CjStubMethod(usage = "枚举主题中的styleid")
	String[] enumStyle(@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid);

	@CjStubMethod(usage = "获取样式")
	Style getStyle(@CjStubInParameter(key = "styleid", usage = "样式标识") String styleid,
			@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid);

	@CjStubMethod(usage = "添除主题所有的样式")
	void emptyStyle(@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid);

	@CjStubMethod(usage = "为主题指定默认样式。当样式标识为空时则清除主题样式")
	void setDefaultStyle(@CjStubInParameter(key = "styleid", usage = "样式标识") String styleid,
			@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid);

	@CjStubMethod(usage = "获取主题中的样式")
	Style getDefaultStyle(@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid);

	@CjStubMethod(usage = "样式在主题中是否存在")
	boolean existsStyle(@CjStubInParameter(key = "styleid", usage = "样式标识") String styleid,
			@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid);

}
