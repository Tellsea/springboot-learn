package cn.tellsea.web;

import cn.tellsea.manage.QuartzJobManage;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quartz")
public class QuartzJobController {

    @Autowired
    private QuartzJobManage quartzJobManage;

    /**
     * 开始执行所有任务
     */
    @RequestMapping("/start")
    public void startQuartzJob() {
        try {
            quartzJobManage.startJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Job信息
     *
     * @param name
     * @param group
     * @return
     */
    @RequestMapping("/info")
    public String getQuartzJob(String name, String group) {
        String info = null;
        try {
            info = quartzJobManage.getJobInfo(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 修改某个任务的执行时间
     *
     * @param name
     * @param group
     * @param time
     * @return
     */
    @RequestMapping("/modify")
    public boolean modifyQuartzJob(String name, String group, String time) {
        boolean flag = true;
        try {
            flag = quartzJobManage.modifyJob(name, group, time);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 暂停某个任务
     *
     * @param name
     * @param group
     */
    @RequestMapping(value = "/pause")
    public void pauseQuartzJob(String name, String group) {
        try {
            quartzJobManage.pauseJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复某个任务
     *
     * @param name
     * @param group
     */
    @RequestMapping(value = "/resumeJob")
    public void resumeJob(String name, String group) {
        try {
            quartzJobManage.resumeJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停所有任务
     */
    @RequestMapping(value = "/pauseAll")
    public void pauseAllQuartzJob() {
        try {
            quartzJobManage.pauseAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复所有任务
     */
    @RequestMapping(value = "/resumeAllJob")
    public void resumeJob() {
        try {
            quartzJobManage.resumeAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除某个任务
     *
     * @param name
     * @param group
     */
    @RequestMapping(value = "/delete")
    public void deleteJob(String name, String group) {
        try {
            quartzJobManage.deleteJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
