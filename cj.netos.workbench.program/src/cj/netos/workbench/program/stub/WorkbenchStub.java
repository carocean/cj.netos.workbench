package cj.netos.workbench.program.stub;

import java.util.List;

import cj.netos.workbench.args.Agency;
import cj.netos.workbench.args.Folder;
import cj.netos.workbench.args.Theme;
import cj.netos.workbench.args.Viewport;
import cj.netos.workbench.args.WorkbenchInfo;
import cj.netos.workbench.bs.IWorkbenchBS;
import cj.netos.workbench.stub.IWorkbenchStub;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.gateway.stub.GatewayAppSiteRestStub;

@CjService(name = "/workbench.service")
public class WorkbenchStub extends GatewayAppSiteRestStub implements IWorkbenchStub {
	@CjServiceRef(refByName = "WBEngine.workbenchBS")
	IWorkbenchBS workbenchBS;

	@Override
	public String createWorkbench(String name, String creator, String desc) throws CircuitException {
		return workbenchBS.createWorkbench(name, creator, desc);
	}

	@Override
	public void removeWorkbench(String wbid) {
		this.workbenchBS.removeWorkbench(wbid);
	}

	@Override
	public List<WorkbenchInfo> pageWorkbenches(int currPage, int pageSize) {
		return this.workbenchBS.pageWorkbenches(currPage, pageSize);
	}

	@Override
	public WorkbenchInfo getWorkbench(String wbid) {
		return this.workbenchBS.getWorkbench(wbid);
	}

	@Override
	public Agency getAgency(String wbid, String path) {
		return this.workbenchBS.getAgency(wbid, path);
	}

	@Override
	public void addChildFolder(String wbid, String parent, String folderCode, String folderName) {
		this.workbenchBS.addChildFolder(wbid, parent, folderCode, folderName);
	}

	@Override
	public void deleteFolder(String wbid, String path) {
		this.workbenchBS.deleteFolder(wbid, path);
	}

	@Override
	public Folder getFolder(String wbid, String path) {
		return this.workbenchBS.getFolder(wbid, path);
	}

	@Override
	public List<Folder> listChildrenFolder(String wbid, String parent) {
		return this.workbenchBS.listChildrenFolder(wbid, parent);
	}

	@Override
	public boolean existsDir(String wbid, String path) {
		return this.workbenchBS.existsDir(wbid, path);
	}

	@Override
	public List<Agency> listAgency(String wbid, String parent) {
		// TODO Auto-generated method stub
		return this.workbenchBS.listAgency(wbid, parent);
	}

	@Override
	public String getHome(String wbid) {
		// TODO Auto-generated method stub
		return workbenchBS.getHome(wbid);
	}

	@Override
	public void setHome(String wbid, String path) {
		// TODO Auto-generated method stub
		workbenchBS.setHome(wbid, path);
	}

	@Override
	public void setTheme(String wbid, String themeid) {
		// TODO Auto-generated method stub
		workbenchBS.setTheme(wbid, themeid);
	}

	@Override
	public Theme getUsingTheme(String wbid) {
		// TODO Auto-generated method stub
		return workbenchBS.getUsingTheme(wbid);
	}

	@Override
	public String addAgency(String wbid, Agency agency) {
		// TODO Auto-generated method stub
		return workbenchBS.addAgency(wbid, agency);
	}

	@Override
	public void removeAgency(String wbid, String path) {
		// TODO Auto-generated method stub
		workbenchBS.removeAgency(wbid, path);
	}

	@Override
	public boolean existsAgency(String wbid, String path) {
		// TODO Auto-generated method stub
		return workbenchBS.existsAgency(wbid, path);
	}

	@Override
	public String addViewport(String wbid, Viewport viewport) {
		// TODO Auto-generated method stub
		return workbenchBS.addViewport(wbid, viewport);
	}

	@Override
	public void removeViewport(String wbid, String path) {
		// TODO Auto-generated method stub
		workbenchBS.removeViewport(wbid, path);
	}

	@Override
	public Viewport getViewport(String wbid, String path) {
		// TODO Auto-generated method stub
		return workbenchBS.getViewport(wbid, path);
	}

	@Override
	public boolean existsViewport(String wbid, String path) {
		// TODO Auto-generated method stub
		return workbenchBS.existsViewport(wbid, path);
	}

	@Override
	public List<Viewport> listViewport(String wbid, String parent) {
		// TODO Auto-generated method stub
		return workbenchBS.listViewport(wbid, parent);
	}

}
