package cj.netos.workbench.stub;

import java.util.ArrayList;
import java.util.List;

import cj.netos.workbench.args.Agency;
import cj.netos.workbench.args.Folder;
import cj.netos.workbench.args.Theme;
import cj.netos.workbench.args.Viewport;
import cj.netos.workbench.args.WorkbenchInfo;
import cj.studio.ecm.net.CircuitException;
import cj.studio.gateway.stub.annotation.CjStubInContentKey;
import cj.studio.gateway.stub.annotation.CjStubInParameter;
import cj.studio.gateway.stub.annotation.CjStubMethod;
import cj.studio.gateway.stub.annotation.CjStubReturn;
import cj.studio.gateway.stub.annotation.CjStubService;

@CjStubService(bindService = "/workbench.service", usage = "工作台")
public interface IWorkbenchStub {
	@CjStubMethod(usage = "添加工作台")
	@CjStubReturn(usage = "返回工作台标识")
	String createWorkbench(@CjStubInParameter(key = "name", usage = "工作台中文名") String name,
			@CjStubInParameter(key = "creator", usage = "工作台创建者") String creator,
			@CjStubInParameter(key = "desc", usage = "注释，如果有。") String desc) throws CircuitException;

	@CjStubMethod(usage = "移除工作台")
	void removeWorkbench(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid);

	@CjStubMethod(usage = "获取一页工作台")
	@CjStubReturn(elementType = WorkbenchInfo.class, usage = "工作台集合")
	List<WorkbenchInfo> pageWorkbenches(@CjStubInParameter(key = "currPage", usage = "当前页") int currPage,
			@CjStubInParameter(key = "pageSize", usage = "页大小") int pageSize);

	@CjStubMethod(usage = "获取工作台")
	WorkbenchInfo getWorkbench(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid);

	@CjStubMethod(usage = "获取Agency")
	Agency getAgency(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "path", usage = "所在路径") String path);

	@CjStubMethod(usage = "添加文件夹")
	void addChildFolder(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "parent", usage = "父路径") String parent,
			@CjStubInParameter(key = "folderCode", usage = "文件夹编码") String folderCode,
			@CjStubInParameter(key = "folderName", usage = "文件夹名") String folderName);

	@CjStubMethod(usage = "添加文件夹")
	void deleteFolder(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "path", usage = "路径") String path);

	@CjStubMethod(usage = "获取文件夹信息")
	Folder getFolder(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "path", usage = "路径") String path);

	@CjStubMethod(usage = "列出子文件夹")
	@CjStubReturn(elementType = Folder.class, usage = "文件夹集合")
	List<Folder> listChildrenFolder(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "parent", usage = "父路径") String parent);

	@CjStubMethod(usage = "是否存在目录")
	boolean existsDir(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "path", usage = "路径") String path);

	@CjStubMethod(usage = "列出子文件夹")
	@CjStubReturn(elementType = Agency.class, usage = "代理集合")
	List<Agency> listAgency(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "parent", usage = "父路径") String parent);

	@CjStubMethod(usage = "获取工作台的主页路径")
	String getHome(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid);

	@CjStubMethod(usage = "设置工作台的主页路径")
	void setHome(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "path", usage = "主页agency路径") String path);

	@CjStubMethod(usage = "设置工作台的主题")
	void setTheme(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "themeid", usage = "主题标识") String themeid);

	@CjStubMethod(usage = "获取工作台的主题")
	Theme getUsingTheme(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid);

	@CjStubMethod(command = "post", usage = "添加代理，并返回代理路径")
	String addAgency(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInContentKey(key = "agency", usage = "代理对象") Agency agency);

	@CjStubMethod(usage = "移除代理")
	void removeAgency(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "path", usage = "agency路径") String path);

	@CjStubMethod(usage = "代理是否存在")
	boolean existsAgency(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "path", usage = "agency路径") String path);

	@CjStubMethod(usage = "添加视口,并返回视口路径", command = "post")
//视口也放在文件夹中，扩展名为:.viewport
	String addViewport(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInContentKey(key = "viewport", usage = "视口信息") Viewport viewport);

	@CjStubMethod(usage = "移除视口")
	void removeViewport(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "path", usage = "viewport路径") String path);

	@CjStubMethod(usage = "获取视口")
	@CjStubReturn(type = Viewport.class, usage = "视口对象")
	Viewport getViewport(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "path", usage = "viewport路径") String path);

	@CjStubMethod(usage = "获取视口")
	boolean existsViewport(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "path", usage = "viewport路径") String path);

	@CjStubMethod(usage = "列出视口")
	@CjStubReturn(type = ArrayList.class, elementType = Viewport.class, usage = "视口对象")
	List<Viewport> listViewport(@CjStubInParameter(key = "wbid", usage = "工作台标识") String wbid,
			@CjStubInParameter(key = "parent", usage = "所在路径") String parent);

}
