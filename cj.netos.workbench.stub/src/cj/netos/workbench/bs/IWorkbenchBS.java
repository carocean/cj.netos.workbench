package cj.netos.workbench.bs;

import java.util.List;

import cj.netos.workbench.args.Agency;
import cj.netos.workbench.args.Folder;
import cj.netos.workbench.args.Theme;
import cj.netos.workbench.args.Viewport;
import cj.netos.workbench.args.WorkbenchInfo;
import cj.studio.ecm.net.CircuitException;

public interface IWorkbenchBS {
	String createWorkbench(String name, String creator, String desc) throws CircuitException;

	void removeWorkbench(String wbid);

	List<WorkbenchInfo> pageWorkbenches(int currPage, int pageSize);

	WorkbenchInfo getWorkbench(String wbid);

	Agency getAgency(String wbid, String path);

	void addChildFolder(String wbid, String parent, String folderCode, String folderName);

	void deleteFolder(String wbid, String path);

	Folder getFolder(String wbid, String path);

	List<Folder> listChildrenFolder(String wbid, String parent);

	boolean existsDir(String wbid, String path);

	List<Agency> listAgency(String wbid, String parent);

	String getHome(String wbid);

	void setHome(String wbid, String path);

	void setTheme(String wbid, String themeid);

	Theme getUsingTheme(String wbid);

	String addAgency(String wbid, Agency agency);

	void removeAgency(String wbid, String path);

	boolean existsAgency(String wbid, String path);

	String addViewport(String wbid, Viewport viewport);

	void removeViewport(String wbid, String path);

	Viewport getViewport(String wbid, String path);

	boolean existsViewport(String wbid, String path);

	List<Viewport> listViewport(String wbid, String parent);

}
