package cj.netos.workbench.plugin.wbcore.bs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.workbench.args.Display;
import cj.netos.workbench.args.Style;
import cj.netos.workbench.args.Theme;
import cj.netos.workbench.bs.IThemeBS;
import cj.studio.ecm.EcmException;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.ultimate.util.StringUtil;

@CjService(name = "themeBS")
public class ThemeBS implements IThemeBS {
	@CjServiceRef(refByName = "mongodb.workbench.home")
	ICube home;

	@Override
	public void addTheme(String themeid, String name, String creator, String desc) {
		if(StringUtil.isEmpty(themeid)) {
			throw new EcmException("未指定主题标识");
		}
		if (getTheme(themeid) != null) {
			throw new EcmException("已存在主题：" + name);
		}
		Theme theme = new Theme();
		theme.setCreator(creator);
		theme.setCtime(System.currentTimeMillis());
		theme.setDesc(desc);
		theme.setName(name);
		theme.setId(themeid);
		home.saveDoc("themes", new TupleDocument<>(theme));
	}

	@Override
	public void removeTheme(String themeid) {
		home.deleteDocOne("themes", String.format("{'tuple.id':'%s'}",themeid));
	}

	@Override
	public List<Theme> pageTheme(long currPage, int pageSize) {
		String cjql = String.format("select {'tuple':'*'}.skip(%s).limit(%s) from tuple themes %s where {}", currPage,
				pageSize, Theme.class.getName());
		IQuery<Theme> q = home.createQuery(cjql);
		List<IDocument<Theme>> docs = q.getResultList();
		List<Theme> list = new ArrayList<Theme>();
		for (IDocument<Theme> doc : docs) {
			Theme theme = doc.tuple();
			list.add(theme);
		}
		return list;
	}

	@Override
	public Theme getTheme(String themeid) {
		String cjql = String.format("select {'tuple':'*'} from tuple themes %s where {'tuple.id':'%s'}",
				Theme.class.getName(), themeid);
		IQuery<Theme> q = home.createQuery(cjql);
		IDocument<Theme> doc = q.getSingleResult();
		if (doc == null)
			return null;
		return doc.tuple();
	}

	@Override
	public void addDisplay(String displayid, String themeid, String name, String desc) {
		if(StringUtil.isEmpty(displayid)) {
			throw new EcmException("未指定显示器标识");
		}
		if (getDisplay(themeid, displayid) != null) {
			throw new EcmException("已存在显示器：" + displayid + "@" + themeid);
		}
		Display display = new Display();
		display.setId(displayid);
		display.setCtime(System.currentTimeMillis());
		display.setName(name);
		display.setDesc(desc);
		display.setTheme(themeid);
		home.saveDoc("displays", new TupleDocument<>(display));
	}
	@Override
	public Display getDisplay(String themeid, String displayid) {
		String cjql = String.format(
				"select {'tuple':'*'} from tuple displays %s where {'tuple.id':'%s','tuple.theme':'%s'}",
				Display.class.getName(), displayid, themeid);
		IQuery<Display> q = home.createQuery(cjql);
		IDocument<Display> doc = q.getSingleResult();
		if (doc == null) {
			return null;
		}
		return doc.tuple();
	}

	@Override
	public void removeDisplay(String themeid, String displayid) {
		home.deleteDocOne("displays", String.format("{'tuple.theme':'%s','tuple.id':'%s'}", themeid, displayid));
	}

	@Override
	public List<Display> listDisplay(String themeid) {
		String cjql = String.format("select {'tuple':'*'} from tuple displays %s where {'tuple.theme':'%s'}",
				Display.class.getName(), themeid);
		IQuery<Display> q = home.createQuery(cjql);
		List<IDocument<Display>> docs = q.getResultList();
		List<Display> list = new ArrayList<>();
		for (IDocument<Display> doc : docs) {
			Display d = doc.tuple();
			list.add(d);
		}
		return list;
	}

	@Override
	public void addStyle(String styleid, String themeid, String name) {
		if(StringUtil.isEmpty(styleid)) {
			throw new EcmException("未指定样式标识");
		}
		if (existsStyle(styleid, themeid)) {
			throw new EcmException("样式已经存在：" + styleid + "@" + themeid);
		}
		Style style = new Style();
		style.setId(styleid);
		style.setTheme(themeid);
		style.setName(name);
		home.saveDoc("styles", new TupleDocument<>(style));
	}

	@Override
	public boolean existsStyle(String styleid, String themeid) {
		long cnt = home.tupleCount("styles", String.format("{'tuple.id':'%s','tuple.theme':'%s'}", styleid, themeid));
		return cnt > 0;
	}

	@Override
	public void emptyStyle(String themeid) {
		home.deleteDocs("styles", String.format("{'tuple.theme':'%s'}", themeid));
	}

	@Override
	public Style getDefaultStyle(String themeid) {
		Theme theme = getTheme(themeid);
		if (theme == null)
			return null;
		String styleid = theme.getStyle();
		if (StringUtil.isEmpty(styleid)) {
			return null;
		}
		return getStyle(styleid, themeid);
	}

	@Override
	public void removeStyle(String styleid, String themeid) {
		home.deleteDocOne("styles", String.format("{'tuple.theme':'%s','tuple.id':'%s'}", themeid, styleid));
	}

	@Override
	public void setDefaultStyle(String styleid, String themeid) {
		Bson filter = Document.parse(String.format("{'tuple.id':'%s'}", themeid));
		Bson update = null;
		if (!StringUtil.isEmpty(styleid)) {
			update = Document.parse(String.format("{$set:{'tuple.style':'%s'}}", styleid));
		} else {
			update = Document.parse(String.format("{$unset:{'tuple.style':''}}"));
		}
		home.updateDocOne("themes", filter, update);
	}

	@Override
	public String[] enumStyle(String themeid) {
		String cjql = String.format("select {'tuple.id':1} from tuple styles %s where {'tuple.theme':'%s'}",
				HashMap.class.getName(), themeid);
		IQuery<HashMap<String, Object>> q = home.createQuery(cjql);
		List<IDocument<HashMap<String, Object>>> docs = q.getResultList();
		List<String> list = new ArrayList<String>();
		for (IDocument<HashMap<String, Object>> doc : docs) {
			list.add((String) doc.tuple().get("id"));
		}
		return list.toArray(new String[0]);
	}

	@Override
	public Style getStyle(String styleid, String themeid) {
		String cjql = String.format(
				"select {'tuple':'*'} from tuple styles %s where {'tuple.id':'%s','tuple.theme':'%s'}",
				Style.class.getName(), styleid, themeid);
		IQuery<Style> q = home.createQuery(cjql);
		IDocument<Style> doc = q.getSingleResult();
		if (doc == null)
			return null;
		return doc.tuple();
	}

}
