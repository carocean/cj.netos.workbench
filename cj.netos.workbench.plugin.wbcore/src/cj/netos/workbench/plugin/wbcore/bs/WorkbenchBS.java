package cj.netos.workbench.plugin.wbcore.bs;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.workbench.args.Agency;
import cj.netos.workbench.args.Folder;
import cj.netos.workbench.args.Theme;
import cj.netos.workbench.args.Viewport;
import cj.netos.workbench.args.WorkbenchInfo;
import cj.netos.workbench.bs.IThemeBS;
import cj.netos.workbench.bs.IWorkbenchBS;
import cj.studio.ecm.EcmException;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.ultimate.util.StringUtil;

@CjService(name = "workbenchBS")
public class WorkbenchBS implements IWorkbenchBS {
	@CjServiceRef(refByName = "mongodb.workbench.home")
	ICube home;
	@CjServiceRef
	IThemeBS themeBS;

	@Override
	public String createWorkbench(String name, String creator, String desc) throws CircuitException {
		WorkbenchInfo wb = new WorkbenchInfo();
		wb.setCtime(System.currentTimeMillis());
		wb.setDesc(desc);
		wb.setName(name);
		wb.setOwner(creator);
		String id = home.saveDoc("workbenches", new TupleDocument<>(wb));
		return id;
	}

	@Override
	public void removeWorkbench(String wbid) {
		home.deleteDoc("workbenches", wbid);
	}

	@Override
	public List<WorkbenchInfo> pageWorkbenches(int currPage, int pageSize) {
		String cjql = String.format("select {'tuple':'*'}.skip(%s).limit(%s) from tuple workbenches %s where {}"
				, currPage, pageSize,WorkbenchInfo.class.getName());
		IQuery<WorkbenchInfo> q = home.createQuery(cjql);
		List<IDocument<WorkbenchInfo>> docs = q.getResultList();
		List<WorkbenchInfo> list = new ArrayList<>();
		for (IDocument<WorkbenchInfo> doc : docs) {
			WorkbenchInfo info = doc.tuple();
			info.setWbid(doc.docid());
			list.add(info);
		}
		return list;
	}

	@Override
	public WorkbenchInfo getWorkbench(String wbid) {
		String cjql = String.format("select {'tuple':'*'} from tuple workbenches %s where {'_id':ObjectId('%s')}",
				WorkbenchInfo.class.getName(), wbid);
		IQuery<WorkbenchInfo> q = home.createQuery(cjql);
		IDocument<WorkbenchInfo> doc = q.getSingleResult();
		if (doc == null)
			return null;
		doc.tuple().setWbid(doc.docid());
		return doc.tuple();
	}

	@Override
	public Agency getAgency(String wbid, String path) {
		int pos = path.lastIndexOf("/");
		if (pos < 0) {
			throw new EcmException("路径格式错误：" + path);
		}
		String parent = path.substring(0, pos);
		while (parent.endsWith("/")) {
			parent = parent.substring(0, parent.length() - 1);
		}
		String name = path.substring(pos + 1, path.length());
		pos = name.lastIndexOf(".");
		if (pos < 0) {
			throw new EcmException("路径格式错误，缺少扩展名：" + path);
		}
		String agencyCode = name.substring(0, pos);
		String ext = name.substring(pos + 1, name.length());
		if (!"agency".equals(ext)) {
			throw new EcmException("路径格式错误，扩展名不是.agency：" + path);
		}
		String cjql = String.format(
				"select {'tuple':'*'} from tuple agencies %s where {'tuple.workbench':'%s','tuple.path':'%s','tuple.code':'%s'}",
				Agency.class.getName(), wbid, parent, agencyCode);
		IQuery<Agency> q = home.createQuery(cjql);
		IDocument<Agency> doc = q.getSingleResult();
		if (doc == null)
			return null;
		return doc.tuple();
	}

	@Override
	public void addChildFolder(String wbid, String parent, String folderCode, String folderName) {
		while (parent.endsWith("/")) {
			parent = parent.substring(0, parent.length() - 1);
		}
		if(StringUtil.isEmpty(parent)) {
			parent="/";
		}
		String fn = String.format("%s/%s", parent, folderCode);
		if (existsDir(wbid, fn)) {
			throw new EcmException("已存在文件夹:" + folderCode);
		}
		Folder folder = new Folder();
		folder.setFolderCode(folderCode);
		folder.setParent(parent);
		folder.setFolderName(folderName);
		folder.setWorkbench(wbid);
		home.saveDoc("folders", new TupleDocument<>(folder));
	}

	@Override
	public void deleteFolder(String wbid, String path) {
		if ("/".equals(path)) {
			throw new EcmException("试图删除根文件夹");
		}
		if (!path.startsWith("/")) {
			throw new EcmException("路径错误：" + path);
		}
		while (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		int pos = path.lastIndexOf("/");
		String parent = path.substring(0, pos);
		if (StringUtil.isEmpty(parent)) {
			parent = "/";
		}
		String code = path.substring(pos + 1, path.length());
		home.deleteDocOne("folders", String
				.format("{'tuple.workbench':'%s','tuple.parent':'%s','tuple.folderCode':'%s'}", wbid, parent, code));
	}

	@Override
	public Folder getFolder(String wbid, String path) {
		if ("/".equals(path)) {
			Folder f = new Folder();
			f.setFolderCode("/");
			f.setFolderName("根");
			f.setParent(null);
			f.setWorkbench(wbid);
			return f;
		}
		if (!path.startsWith("/")) {
			throw new EcmException("路径错误：" + path);
		}
		while (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		int pos = path.lastIndexOf("/");
		String parent = path.substring(0, pos);
		if (StringUtil.isEmpty(parent)) {
			parent = "/";
		}
		String code = path.substring(pos + 1, path.length());
		String cjql = String.format(
				"select {'tuple':'*'} from tuple folders %s where {'tuple.workbench':'%s','tuple.parent':'%s','tuple.folderCode':'%s'}",
				Folder.class.getName(), wbid, parent, code);
		IQuery<Folder> q = home.createQuery(cjql);
		IDocument<Folder> doc = q.getSingleResult();
		if (doc == null)
			return null;
		return doc.tuple();
	}

	@Override
	public List<Folder> listChildrenFolder(String wbid, String parent) {
		if (!parent.startsWith("/")) {
			throw new EcmException("路径错误：" + parent);
		}
		while (parent.endsWith("/")) {
			parent = parent.substring(0, parent.length() - 1);
		}
		if(StringUtil.isEmpty(parent)) {
			parent="/";
		}
		String cjql = String.format(
				"select {'tuple':'*'} from tuple folders %s where {'tuple.workbench':'%s','tuple.parent':'%s'}",
				Folder.class.getName(), wbid, parent);
		IQuery<Folder> q = home.createQuery(cjql);
		List<IDocument<Folder>> docs = q.getResultList();
		List<Folder> list = new ArrayList<Folder>();
		for (IDocument<Folder> doc : docs) {
			list.add(doc.tuple());
		}
		return list;
	}

	@Override
	public boolean existsDir(String wbid, String path) {
		if ("/".equals(path)) {
			return true;// 根永远存在
		}
		if (!path.startsWith("/")) {
			throw new EcmException("路径错误：" + path);
		}
		while (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		int pos = path.lastIndexOf("/");
		String parent = path.substring(0, pos);
		if (StringUtil.isEmpty(parent)) {
			parent = "/";
		}
		String code = path.substring(pos + 1, path.length());
		long cnt = home.tupleCount("folders", String
				.format("{'tuple.workbench':'%s','tuple.parent':'%s','tuple.folderCode':'%s'}", wbid, parent, code));
		return cnt > 0;
	}

	@Override
	public List<Agency> listAgency(String wbid, String parent) {
		String cjql = String.format(
				"select {'tuple':'*'} from tuple agencies %s where {'tuple.workbench':'%s','tuple.path':{ $regex: '^%s', $options: 'i' }}",
				Agency.class.getName(), wbid, parent.replace("/", "\\/"));
		IQuery<Agency> q = home.createQuery(cjql);
		List<IDocument<Agency>> docs = q.getResultList();
		List<Agency> list = new ArrayList<Agency>();
		for (IDocument<Agency> doc : docs) {
			list.add(doc.tuple());
		}
		return list;
	}

	@Override
	public String getHome(String wbid) {
		String cjql = String.format("select {'tuple.home':1} from tuple workbenches %s where {'_id':ObjectId('%s')}",
				WorkbenchInfo.class.getName(), wbid);
		IQuery<WorkbenchInfo> q = home.createQuery(cjql);
		IDocument<WorkbenchInfo> doc = q.getSingleResult();
		if (doc == null)
			return null;
		return doc.tuple().getHome();
	}

	@Override
	public void setHome(String wbid, String path) {
		Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", wbid));
		Bson update = Document.parse(String.format("{$set:{'tuple.home':'%s'}}", path));
		home.updateDocOne("workbenches", filter, update);
	}

	@Override
	public void setTheme(String wbid, String themeid) {
		Bson filter = Document.parse(String.format("{'_id':ObjectId('%s')}", wbid));
		Bson update = Document.parse(String.format("{$set:{'tuple.theme':'%s'}}", themeid));
		home.updateDocOne("workbenches", filter, update);
	}

	@Override
	public Theme getUsingTheme(String wbid) {
		String cjql = String.format("select {'tuple.theme':1} from tuple workbenches %s where {'_id':ObjectId('%s')}",
				WorkbenchInfo.class.getName(), wbid);
		IQuery<WorkbenchInfo> q = home.createQuery(cjql);
		IDocument<WorkbenchInfo> doc = q.getSingleResult();
		if (doc == null)
			return null;

		return themeBS.getTheme(doc.tuple().getTheme());
	}

	@Override
	public String addAgency(String wbid, Agency agency) {
		if (StringUtil.isEmpty(agency.getCode()) || StringUtil.isEmpty(agency.getPath())) {
			throw new EcmException("agency的路径或编码为空");
		}
		String path = String.format("%s/%s.agency", agency.getPath(), agency.getCode());
		if (existsAgency(wbid, path)) {
			throw new EcmException("agency已存在：" + path);
		}
		agency.setWorkbench(wbid);
		home.saveDoc("agencies", new TupleDocument<>(agency));
		return path;
	}

	@Override
	public void removeAgency(String wbid, String path) {
		int pos = path.lastIndexOf("/");
		if (pos < 0) {
			throw new EcmException("路径格式错误：" + path);
		}
		String parent = path.substring(0, pos);
		while (parent.endsWith("/")) {
			parent = parent.substring(0, parent.length() - 1);
		}
		String name = path.substring(pos + 1, path.length());
		pos = name.lastIndexOf(".");
		if (pos < 0) {
			throw new EcmException("路径格式错误，缺少扩展名：" + path);
		}
		String agencyCode = name.substring(0, pos);
		String ext = name.substring(pos + 1, name.length());
		if (!"agency".equals(ext)) {
			throw new EcmException("路径格式错误，扩展名不是.agency：" + path);
		}

		home.deleteDocOne("agencies", String.format("{'tuple.workbench':'%s','tuple.path':'%s','tuple.code':'%s'}",
				wbid, parent, agencyCode));
	}

	@Override
	public boolean existsAgency(String wbid, String path) {
		int pos = path.lastIndexOf("/");
		if (pos < 0) {
			throw new EcmException("路径格式错误：" + path);
		}
		String parent = path.substring(0, pos);
		while (parent.endsWith("/")) {
			parent = parent.substring(0, parent.length() - 1);
		}
		String name = path.substring(pos + 1, path.length());
		pos = name.lastIndexOf(".");
		if (pos < 0) {
			throw new EcmException("路径格式错误，缺少扩展名：" + path);
		}
		String agencyCode = name.substring(0, pos);
		String ext = name.substring(pos + 1, name.length());
		if (!"agency".equals(ext)) {
			throw new EcmException("路径格式错误，扩展名不是.agency：" + path);
		}
		return home.tupleCount("agencies", String.format("{'tuple.workbench':'%s','tuple.path':'%s','tuple.code':'%s'}",
				wbid, parent, agencyCode)) > 0;
	}

	@Override
	public String addViewport(String wbid, Viewport viewport) {
		if (StringUtil.isEmpty(viewport.getCode()) || StringUtil.isEmpty(viewport.getPath())) {
			throw new EcmException("视口的路径或编码为空");
		}
		String path = String.format("%s/%s.viewport", viewport.getPath(), viewport.getCode());
		if (existsViewport(wbid, path)) {
			throw new EcmException("viewport已存在：" + path);
		}
		viewport.setWorkbench(wbid);
		home.saveDoc("viewports", new TupleDocument<>(viewport));
		return path;
	}

	@Override
	public void removeViewport(String wbid, String path) {
		int pos = path.lastIndexOf("/");
		if (pos < 0) {
			throw new EcmException("路径格式错误：" + path);
		}
		String parent = path.substring(0, pos);
		while (parent.endsWith("/")) {
			parent = parent.substring(0, parent.length() - 1);
		}
		String name = path.substring(pos + 1, path.length());
		pos = name.lastIndexOf(".");
		if (pos < 0) {
			throw new EcmException("路径格式错误，缺少扩展名：" + path);
		}
		String code = name.substring(0, pos);
		String ext = name.substring(pos + 1, name.length());
		if (!"viewport".equals(ext)) {
			throw new EcmException("路径格式错误，扩展名不是.viewport：" + path);
		}

		home.deleteDocOne("viewports",
				String.format("{'tuple.workbench':'%s','tuple.path':'%s','tuple.code':'%s'}", wbid, parent, code));
	}

	@Override
	public Viewport getViewport(String wbid, String path) {
		int pos = path.lastIndexOf("/");
		if (pos < 0) {
			throw new EcmException("路径格式错误：" + path);
		}
		String parent = path.substring(0, pos);
		while (parent.endsWith("/")) {
			parent = parent.substring(0, parent.length() - 1);
		}
		String name = path.substring(pos + 1, path.length());
		pos = name.lastIndexOf(".");
		if (pos < 0) {
			throw new EcmException("路径格式错误，缺少扩展名：" + path);
		}
		String agencyCode = name.substring(0, pos);
		String ext = name.substring(pos + 1, name.length());
		if (!"viewport".equals(ext)) {
			throw new EcmException("路径格式错误，扩展名不是.viewport：" + path);
		}
		String cjql = String.format(
				"select {'tuple':'*'} from tuple viewports %s where {'tuple.workbench':'%s','tuple.path':'%s','tuple.code':'%s'}",
				Viewport.class.getName(), wbid, parent, agencyCode);
		IQuery<Viewport> q = home.createQuery(cjql);
		IDocument<Viewport> doc = q.getSingleResult();
		if (doc == null)
			return null;
		return doc.tuple();
	}

	@Override
	public boolean existsViewport(String wbid, String path) {
		int pos = path.lastIndexOf("/");
		if (pos < 0) {
			throw new EcmException("路径格式错误：" + path);
		}
		String parent = path.substring(0, pos);
		while (parent.endsWith("/")) {
			parent = parent.substring(0, parent.length() - 1);
		}
		String name = path.substring(pos + 1, path.length());
		pos = name.lastIndexOf(".");
		if (pos < 0) {
			throw new EcmException("路径格式错误，缺少扩展名：" + path);
		}
		String code = name.substring(0, pos);
		String ext = name.substring(pos + 1, name.length());
		if (!"viewport".equals(ext)) {
			throw new EcmException("路径格式错误，扩展名不是.viewport：" + path);
		}
		return home.tupleCount("viewports",
				String.format("{'tuple.workbench':'%s','tuple.path':'%s','tuple.code':'%s'}", wbid, parent, code)) > 0;
	}

	@Override
	public List<Viewport> listViewport(String wbid, String parent) {
		String cjql = String.format(
				"select {'tuple':'*'} from tuple viewports %s where {'tuple.workbench':'%s','tuple.path':{ $regex: '^%s', $options: 'i' }}",
				Viewport.class.getName(), wbid, parent.replace("/", "\\/"));
		IQuery<Viewport> q = home.createQuery(cjql);
		List<IDocument<Viewport>> docs = q.getResultList();
		List<Viewport> list = new ArrayList<>();
		for (IDocument<Viewport> doc : docs) {
			list.add(doc.tuple());
		}
		return list;
	}

}
