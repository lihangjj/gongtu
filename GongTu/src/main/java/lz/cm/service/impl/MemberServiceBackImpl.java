package lz.cm.service.impl;

import lz.cm.dao.IDeptDAO;
import lz.cm.dao.IJobDAO;
import lz.cm.dao.IRoleDAO;
import lz.cm.service.AbstractService;
import lz.cm.service.IRoleService;
import lz.cm.vo.*;
import lz.util.str.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lz.cm.dao.IMemberDAO;
import lz.cm.service.IMemberServiceBack;

import java.util.*;

@Service
public class MemberServiceBackImpl extends AbstractService implements IMemberServiceBack {

    @Autowired
    private IMemberDAO memberDAO;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IJobDAO jobDAO;
    @Autowired
    private IDeptDAO deptDAO;
    @Autowired
    private IRoleDAO roleDAO;

    @Override
    public Member getMemberById(String mid) {
        try {
            Member member = memberDAO.findById(mid);
            Job job = jobDAO.findById(member.getJobid(), Job.class);
            job.setDept(deptDAO.findById(job.getDeptid(), Dept.class));
            member.setJob(job);
            return member;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Role> getRolesAndActions(String mid) {
        List<Role> allRoles = memberDAO.getAllMemberRoles(mid);//实体类
        for (Role x : allRoles) {
            x.setActions(memberDAO.getAllActionsByRoleId(x.getRoleid()));
        }
        return allRoles;
    }

    @Override
    public Member login(Member member) throws Exception {

        return memberDAO.login(member);
    }

    @Override
    public boolean editEflag(Member member) throws Exception {
        return memberDAO.updateEflag(member);
    }

    @Override
    public Set<String> getAllMemberRolesFlag(String mid) throws Exception {
        List<Role> allRolesV = memberDAO.getAllMemberRoles(mid);//实体类
        Set<String> allRoles = new HashSet<>();
        for (Role x : allRolesV) {
            allRoles.add(x.getFlag());
        }
        return allRoles;
    }

    @Override
    public Set<String> getAllMemberActionsFlag(String mid) throws Exception {
        List<Action> allActionsV = memberDAO.getAllMemberActions(mid);
        Set<String> allActions = new HashSet<>();

        for (Action x : allActionsV) {
            allActions.add(x.getFlag());
        }
        return allActions;
    }

    @Override
    public boolean editMember(Member member) throws Exception {


        return memberDAO.updateMember(member);
    }

    @Override
    public boolean addMember(Member member) throws Exception {
        String[] publicRole = StrUtil.PUBLIC_ROLE.split("-");
        String job = jobDAO.findById(member.getJobid(), Job.class).getJob();
        String[] privateRole = roleDAO.getRoleByJobId(job);//职位的权限
        //将公有数据变成集合
        List<String> roleids = new ArrayList<>(Arrays.asList(publicRole));
        Collections.addAll(roleids, privateRole);//将私有角色加入集合，最后再变成数组
        roleService.plAddRoleToMembers(roleids.toArray(new String[0]), new String[]{member.getMemberid()});
        return memberDAO.doCreate(member);
    }

    @Override
    public Member getVoById(String s) {
        return memberDAO.findById(s, Member.class);
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {

        Map<String, Object> resMap = memberDAO.splitVoByFlag(Member.class, column, keyWord, currentPage, lineSize, getCondition(parameterName, "=", parameterValue));
        List<Member> allMember = (List<Member>) resMap.get("allVo");
        for (Member m : allMember) {
            Job job = jobDAO.findById(m.getJobid(), Job.class);
            job.setDept(deptDAO.findById(job.getDeptid(), Dept.class));
            m.setJob(job);
        }
        resMap.put("allVo", allMember);
        return resMap;
    }


    @Override
    public boolean editSflag(String memberid, int sflag) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("memberid", memberid);
        parameterMap.put("sflag", sflag);
        return memberDAO.doUpdateSflag(parameterMap);
    }

    @Override
    public boolean plSuoOrJie(int sflag, String[] ids) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("sflag", sflag);
        parameterMap.put("ids", ids);

        return memberDAO.doUpdateSflagBatch(parameterMap);
    }

    @Override
    public boolean editStyle(Member member) throws Exception {
        return memberDAO.updateStyle(member);
    }

    @Override
    public boolean editFontStyle(Member member) throws Exception {
        return memberDAO.updateSysFont(member);
    }

    @Override
    public List<String> getAllNames() throws Exception {
        return memberDAO.getAllNames();
    }

    @Override
    public List<Member> getAllMemberIdAndNames() {
        return memberDAO.getAllMemberIdAndNames();
    }

    @Override
    public boolean deleteRoleByMemberid(Member member) throws Exception {
        if (memberDAO.deleteRoleByMemberid(member)) {//先删除原来的角色
            String[] publicRole = StrUtil.PUBLIC_ROLE.split("-");
            String job = jobDAO.findById(member.getJobid(), Job.class).getJob();
            String[] privateRole = roleDAO.getRoleByJobId(job);//职位的权限
            //将公有数据变成集合
            List<String> roleids = new ArrayList<>(Arrays.asList(publicRole));
            Collections.addAll(roleids, privateRole);//将私有角色加入集合，最后再变成数组
            return roleService.plAddRoleToMembers(roleids.toArray(new String[0]), new String[]{member.getMemberid()});
        }
        return false;
    }

    @Override
    public List<Member> getAllExecutive(String status) {
        String jsCondition = "";
        if (status == null || "".equals(status)) {

        } else {
            jsCondition = " AND status='" + status + "'";
        }
        List<Member> allMember = (List<Member>) memberDAO.splitVoByColumns("name", Member.class, null,
                null, null, null, "memberid!='lh'").get("allVo");
        for (Member m : allMember) {
            List<Contract> contracts = (List<Contract>) memberDAO.splitVoByColumns("companyName,contractid",
                    Contract.class, null, null, null, null,
                    "contractid IN(SELECT contractid FROM project where executive='" + m.getName() + "') " + jsCondition + "").get("allVo");
            for (Contract c : contracts) {
                List<Project> projects = (List<Project>) memberDAO.splitVoByColumns("name,type", Project.class, null,
                        null, null, null, "contractid='" + c.getContractid() + "' AND executive='" + m.getName() + "' " + jsCondition + "").get("allVo");

                c.setProjects(projects);
            }
            m.setContracts(contracts);

        }
        return allMember;
    }


}
