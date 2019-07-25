package org.fisco.bcos.model;
/**
 * @author elizayuan
 * 用于映射用户表
 */
public class WegoUser {
   // 用户id
    public int userId;
    //用户名
    public String userName;
    //密码
    public String password;
    //健康状态
    public String healthyBean;
    //食物列表
    public String foodList;
    //皮肤列表
    public String skinList;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHealthyBean() {
        return healthyBean;
    }

    public void setHealthyBean(String healthyBean) {
        this.healthyBean = healthyBean;
    }

    public String getFoodList() {
        return foodList;
    }

    public void setFoodList(String foodList) {
        this.foodList = foodList;
    }

    public String getSkinList() {
        return skinList;
    }

    public void setSkinList(String skinList) {
        this.skinList = skinList;
    }
}
