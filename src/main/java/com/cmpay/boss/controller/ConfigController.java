package com.cmpay.boss.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpay.boss.domain.IpBO;
import com.cmpay.boss.domain.MerchantBO;
import com.cmpay.boss.form.IpManageForm;
import com.cmpay.boss.form.MerchantForm;
import com.cmpay.boss.service.ConfigService;
import com.cmpay.boss.util.Pagination;

/**
 * 配置相关功能
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年10月11日 下午2:44:27
 *
 */
@Controller
public class ConfigController {
    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    ConfigService    configService;

    @RequestMapping(value = "/ipManagement/query_all_ip", method = RequestMethod.GET)
    public String getAllIp(@ModelAttribute("ipManageForm") IpManageForm ipManageForm){
        IpBO ipBO = new IpBO();
        String pageCurrent = ipManageForm.getPageCurrent();
        String pageSize = ipManageForm.getPageSize();

        ipBO.setPageCurrent(Integer.valueOf(pageCurrent));
        ipBO.setPageSize(Integer.valueOf(pageSize));

        Pagination<IpBO> ipBOPagination = configService.getAllIp(ipBO);

        ipManageForm.setPagination(ipBOPagination);

        return "ipmanagelist";

    }

    @RequestMapping(value = "/merchantManagement/query_all_merchant", method = RequestMethod.GET)
    public String getAllMer(@ModelAttribute("merForm") MerchantForm merForm){
    	MerchantBO merchantBO = new MerchantBO();
        String pageCurrent = merForm.getPageCurrent();
        String pageSize = merForm.getPageSize();

        merchantBO.setPageCurrent(Integer.valueOf(pageCurrent));
        merchantBO.setPageSize(Integer.valueOf(pageSize));

        Pagination<MerchantBO> merBOPagination = configService.getAllMer(merchantBO);

        merForm.setPagination(merBOPagination);

        return "merchant/mermanagelist";

    }

    @RequestMapping(value = "/merchantManagement/getMerByPara", method = RequestMethod.POST)
    public String getMerByPara(@ModelAttribute("merForm") MerchantForm merForm){
    	MerchantBO merchantBO = new MerchantBO();
        String pageCurrent = merForm.getPageCurrent();
        String pageSize = merForm.getPageSize();

        merchantBO.setPageCurrent(Integer.valueOf(pageCurrent));
        merchantBO.setPageSize(Integer.valueOf(pageSize));

        if(StringUtils.isNotBlank(merForm.getMerchantid())){
        	merchantBO.setMerchantid(merForm.getMerchantid());
        }

        Pagination<MerchantBO> merBOPagination = configService.getMerByPara(merchantBO);

        merForm.setPagination(merBOPagination);

        return "merchant/mermanagelist";

    }

    @RequestMapping(value = "/ipManagement/query_a_ip", method = RequestMethod.POST)
    public String getIpByPara(@ModelAttribute("ipManageForm") IpManageForm ipManageForm){
        IpBO ipBO = new IpBO();
        String pageCurrent = ipManageForm.getPageCurrent();
        String pageSize = ipManageForm.getPageSize();

        ipBO.setPageCurrent(Integer.valueOf(pageCurrent));
        ipBO.setPageSize(Integer.valueOf(pageSize));

        if(StringUtils.isNotBlank(ipManageForm.getIp())){
        	ipBO.setIp(ipManageForm.getIp());
        }

        Pagination<IpBO> ipBOPagination = configService.getIpByPara(ipBO);

        ipManageForm.setPagination(ipBOPagination);

        return "ipmanagelist";

    }

    @RequestMapping(value = "/ipManagement/addIp", method = RequestMethod.GET)
    public String goAddNewPage(@ModelAttribute("ipManageForm") IpManageForm ipManageForm) {

        return "addip";
    }

    @RequestMapping(value = "/merchantManagement/addMer", method = RequestMethod.GET)
    public String goAddNewMerPage(@ModelAttribute("merForm") MerchantForm merForm) {

        return "merchant/addmer";
    }


    @ResponseBody
    @RequestMapping(value = "/ipManagement/addNewIp", method = RequestMethod.POST)
    public Map addNewRole(@ModelAttribute("ipManageForm") IpManageForm ipManageForm) {
        Map resultMap = new HashMap();
        String ip = ipManageForm.getIp();
        String inchannel=ipManageForm.getInchannel();
        String remark=ipManageForm.getRemark();

        if(StringUtils.isBlank(ip)){
        	resultMap.put("statusCode", 300);
            resultMap.put("message", "ip地址不能为空");
            return resultMap;
        }


        resultMap = configService.addNewIp(ip, inchannel, remark);

        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = "/merchantManagement/addNewMer", method = RequestMethod.POST)
    public Map addNewMer(@ModelAttribute("merForm") MerchantForm merForm) {
        Map resultMap = new HashMap();
        String merchantid=merForm.getMerchantid();
        String merchantName=merForm.getMerchantName();
        String ecode=merForm.getEcode();
        String linkman=merForm.getLinkman();
        String mobile=merForm.getMobile();

        if(StringUtils.isBlank(merchantid)||StringUtils.isBlank(merchantName)||StringUtils.isBlank(ecode)||StringUtils.isBlank(linkman)||StringUtils.isBlank(mobile)){
        	resultMap.put("statusCode", 300);
            resultMap.put("message", "必填参数不能为空");
            return resultMap;
        }
        MerchantBO merchantBO = new MerchantBO();
        try {
			BeanUtils.copyProperties(merchantBO, merForm);
		} catch (Exception e) {
			logger.error("cope properties出现异常！！！！");
			e.printStackTrace();
		}

        resultMap = configService.addNewMer(merchantBO);

        return resultMap;
    }


    @RequestMapping(value = "/ipManagement/edit", method = RequestMethod.GET)
    public String modifyFuncDetails(HttpServletRequest request,
    		@ModelAttribute("ipManageForm") IpManageForm ipManageForm) {

        String sid = request.getParameter("sid");
        IpBO ipBO = configService.getById(sid);
        ipManageForm.setIp(ipBO.getIp());
        ipManageForm.setInchannel(ipBO.getInchannel());
        ipManageForm.setStatus(ipBO.getStatus());
        ipManageForm.setCreatetime(ipBO.getCreatetime());
        ipManageForm.setOperator(ipBO.getOperator());

        return "modifyipdetails";
    }

    @RequestMapping(value = "/merchantManagement/edit", method = RequestMethod.GET)
    public String modifyMerDetails(HttpServletRequest request,
    		@ModelAttribute("merForm") MerchantForm merForm) {

        String sid = request.getParameter("sid");
        MerchantBO MerchantBO = configService.getMerById(sid);
        try {
			BeanUtils.copyProperties(merForm, MerchantBO);
		} catch (Exception e) {
			logger.error("cope properties 异常！！！！");
			e.printStackTrace();
		}

        return "merchant/updmer";
    }

    @ResponseBody
    @RequestMapping(value = "/ipManagement/updateIp", method = RequestMethod.POST)
    public Map updateFuncDetails(@ModelAttribute("ipManageForm") IpManageForm ipManageForm) {
        Map resultMap = new HashMap();
        IpBO ipBO = new IpBO();
        String ip=ipManageForm.getIp();
        String inchannel=ipManageForm.getInchannel();
        String status=ipManageForm.getStatus();
        if (StringUtils.isBlank(ip)||StringUtils.isBlank(inchannel)||StringUtils.isBlank(status)) {
            resultMap.put("statusCode", 300);
            resultMap.put("message", "请检查更改参数是否完整");
            return resultMap;
        }
        try {
        	ipBO.setIp(ip);
        	ipBO.setInchannel(inchannel);
        	ipBO.setStatus(status);
        	ipBO.setRemark(ipManageForm.getRemark());
            resultMap = configService.updateIpInfo(ipBO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            resultMap.put("statusCode", 300);
            resultMap.put("message", "操作失败!");

        }
        return resultMap;

    }

    @ResponseBody
    @RequestMapping(value = "/merchantManagement/updateMer", method = RequestMethod.POST)
    public Map updateMer(@ModelAttribute("merForm") MerchantForm merForm) {
        Map resultMap = new HashMap();
        MerchantBO merchantBO = new MerchantBO();
        String id=merForm.getId();

        if (StringUtils.isBlank(id)) {
            resultMap.put("statusCode", 300);
            resultMap.put("message", "请检查更改参数ID是否完整");
            return resultMap;
        }
        try {
        	BeanUtils.copyProperties(merchantBO, merForm);
            resultMap = configService.updateMerInfo(merchantBO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            resultMap.put("statusCode", 300);
            resultMap.put("message", "更新商户操作失败!");

        }
        return resultMap;

    }

}
