package org.zkoss.todoZK.viewmodel;

import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.todoZK.dao.AbstractDB;
import org.zkoss.todoZK.dao.DBProvider;
import org.zkoss.todoZK.exception.MilestoneNotExist;
import org.zkoss.todoZK.exception.WorkspaceNotExist;
import org.zkoss.todoZK.vo.Milestone;
import org.zkoss.todoZK.vo.Workspace;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Div;

public class CardViewVM {
	
	private AbstractDB db = DBProvider.getInstance();
	private List<Milestone> milestones;
	private Milestone milestone;
	private Converter<String, Integer, Div> priorityConverter = new PriorityConverter();
	private Workspace workspace;

	public CardViewVM() {
		Execution exec = Executions.getCurrent();
		String ws = exec.getParameter("ws");
		String ms = exec.getParameter("ms");
		if (ws != null) {
			try {
				workspace = db.getWorkspaceById(Long.parseLong(ws));
				milestones = workspace.getMilestones();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (WorkspaceNotExist e) {
				e.printStackTrace();
			}
		} else if (ms != null) {
			try {
				milestone = db.getMilestoneById(Long.parseLong(ms));
				workspace = db.getWorkspaceById(milestone.getWorkspaceId());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (MilestoneNotExist e) {
				e.printStackTrace();
			} catch (WorkspaceNotExist e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Milestone> getMilestones() {
		return milestones;
	}
	
	public Milestone getMilestone() {
		return milestone;
	}
	
	public Workspace getWorkspace() {
		return workspace;
	}
	
	public Converter<String, Integer, Div> getPriorityConverter() {
		return priorityConverter;
	}
	
	public class PriorityConverter implements Converter<String, Integer, Div> {
		public Integer coerceToBean(String sclass, Div div, BindContext ctx) {
			return null;
		}
		
		public String coerceToUi(Integer priority, Div div, BindContext ctx) {
			switch (priority) {
			case 0:
				return "priority routine";
			case 1:
				return "priority essential";
			case 2:
				return "priority vital";
			case 3:
				return "priority important";
			case 4:
				return "priority urgent";
			default:
				return null;
			}
		}
	}
}
