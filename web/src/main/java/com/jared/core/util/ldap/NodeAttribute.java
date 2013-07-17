package com.jared.core.util.ldap;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class NodeAttribute implements Serializable
{
	private static final long serialVersionUID = -7686322435873120827L;

	String nodeName;
	String accountExpires; // 账户到期
	String adminCount; 
	String badPasswordTime;
	String badPwdCount;
	String c;
	String cn;	// 显示名称
	String co;	// 国家(地区)
	String codePage; 
	String company;	// 单位
	String countryCode;
	String department;	// 部门
	String description; // 描述
	String displayName; // 显示名
	String distinguishedName; // 完整dn
	String facsimileTelephoneNumber; // 传真
	String givenName; // 名
	String homePhone; // 家庭电话
	String initials; // 英文缩写
	String instanceType;
	String ipPhone; // IP电话
	String l; // 省
	String lastlogoff;
	String lastLogon;
	String logonCount;
	String mail; // 邮箱
	List<String> memberOf;
	String mobile; // 移动电话
	String name;
	String objectCategory;
	List<String> objectClass;
	String objectGUID;
	String objectSid;
	String otherTelephone; // 其他电话
	String pager; // 寻呼机
	String physicalDeliveryOfficeName;
	String postalCode; // 邮政信箱
	String postOfficeBox; // 邮政编码
	String primaryGroupID;
	String pwdLastSet;
	String accountName;
	String sAMAccountType;
	String sn; // 姓
	String st; // 市
	String streetAddress; // 街道
	String telephoneNumber; // 电话号码
	String title; // 职务
	String userAccountControl;
	String userPassword;
	String userPrincipalName;
	String uSNChanged;
	String uSNCreated;
	String whenChanged;
	String whenCreated;
	String wWWHomePage;
	String userCertificate; // 证书
	
	List<String> member; // 成员
	
	String operatingSystem;
	String operatingSystemServicePack;
	String operatingSystemVersion;
	String dNSHostName;
	
	HashMap<String, Object> data;
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public NodeAttribute()
	{
		data = new HashMap<String, Object>();
	}
	
	public Object get(String key)
	{
		return data.get(key);
	}
	
	public void put(String key, Object value)
	{
		this.data.put(key, value);
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<String> getObjectClass()
	{
		return objectClass;
	}

	public void setObjectClass(List<String> objectClass)
	{
		this.objectClass = objectClass;
	}

	public String getCn()
	{
		return cn;
	}

	public void setCn(String cn)
	{
		this.cn = cn;
	}

	public String getSn()
	{
		return sn;
	}

	public void setSn(String sn)
	{
		this.sn = sn;
	}

	public String getGivenName()
	{
		return givenName;
	}

	public void setGivenName(String givenName)
	{
		this.givenName = givenName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getDistinguishedName()
	{
		return distinguishedName;
	}

	public void setDistinguishedName(String distinguishedName)
	{
		this.distinguishedName = distinguishedName;
	}

	public String getObjectCategory()
	{
		return objectCategory;
	}

	public void setObjectCategory(String objectCategory)
	{
		this.objectCategory = objectCategory;
	}

	public String getUserCertificate()
	{
		return userCertificate;
	}

	public void setUserCertificate(String userCertificate)
	{
		this.userCertificate = userCertificate;
	}

	public String getNodeName()
	{
		if (this.distinguishedName != null)
		{
			nodeName = distinguishedName;
			int index = nodeName.indexOf(",");
			if (index != -1)
			{
				nodeName = nodeName.substring(0, index);
			}
			return nodeName;
		}
		return null;
	}

	public List<String> getMember() {
		return member;
	}

	public void setMember(List<String> member) {
		this.member = member;
	}

	public List<String> getMemberOf() {
		return memberOf;
	}

	public void setMemberOf(List<String> memberOf) {
		this.memberOf = memberOf;
	}

	public String getLastLogon() {
		return lastLogon;
	}

	public void setLastLogon(String lastLogon) {
		this.lastLogon = lastLogon;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getOperatingSystemServicePack() {
		return operatingSystemServicePack;
	}

	public void setOperatingSystemServicePack(String operatingSystemServicePack) {
		this.operatingSystemServicePack = operatingSystemServicePack;
	}

	public String getOperatingSystemVersion() {
		return operatingSystemVersion;
	}

	public void setOperatingSystemVersion(String operatingSystemVersion) {
		this.operatingSystemVersion = operatingSystemVersion;
	}

	public String getDNSHostName() {
		return dNSHostName;
	}

	public void setDNSHostName(String hostName) {
		dNSHostName = hostName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAccountExpires()
	{
		return accountExpires;
	}

	public void setAccountExpires(String accountExpires)
	{
		this.accountExpires = accountExpires;
	}

	public String getAdminCount()
	{
		return adminCount;
	}

	public void setAdminCount(String adminCount)
	{
		this.adminCount = adminCount;
	}

	public String getBadPasswordTime()
	{
		return badPasswordTime;
	}

	public void setBadPasswordTime(String badPasswordTime)
	{
		this.badPasswordTime = badPasswordTime;
	}

	public String getBadPwdCount()
	{
		return badPwdCount;
	}

	public void setBadPwdCount(String badPwdCount)
	{
		this.badPwdCount = badPwdCount;
	}

	public String getC()
	{
		return c;
	}

	public void setC(String c)
	{
		this.c = c;
	}

	public String getCo()
	{
		return co;
	}

	public void setCo(String co)
	{
		this.co = co;
	}

	public String getCodePage()
	{
		return codePage;
	}

	public void setCodePage(String codePage)
	{
		this.codePage = codePage;
	}

	public String getCountryCode()
	{
		return countryCode;
	}

	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	public String getFacsimileTelephoneNumber()
	{
		return facsimileTelephoneNumber;
	}

	public void setFacsimileTelephoneNumber(String facsimileTelephoneNumber)
	{
		this.facsimileTelephoneNumber = facsimileTelephoneNumber;
	}

	public String getHomePhone()
	{
		return homePhone;
	}

	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}

	public String getInitials()
	{
		return initials;
	}

	public void setInitials(String initials)
	{
		this.initials = initials;
	}

	public String getInstanceType()
	{
		return instanceType;
	}

	public void setInstanceType(String instanceType)
	{
		this.instanceType = instanceType;
	}

	public String getIpPhone()
	{
		return ipPhone;
	}

	public void setIpPhone(String ipPhone)
	{
		this.ipPhone = ipPhone;
	}

	public String getL()
	{
		return l;
	}

	public void setL(String l)
	{
		this.l = l;
	}

	public String getLastlogoff()
	{
		return lastlogoff;
	}

	public void setLastlogoff(String lastlogoff)
	{
		this.lastlogoff = lastlogoff;
	}

	public String getLogonCount()
	{
		return logonCount;
	}

	public void setLogonCount(String logonCount)
	{
		this.logonCount = logonCount;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getObjectGUID()
	{
		return objectGUID;
	}

	public void setObjectGUID(String objectGUID)
	{
		this.objectGUID = objectGUID;
	}

	public String getObjectSid()
	{
		return objectSid;
	}

	public void setObjectSid(String objectSid)
	{
		this.objectSid = objectSid;
	}

	public String getOtherTelephone()
	{
		return otherTelephone;
	}

	public void setOtherTelephone(String otherTelephone)
	{
		this.otherTelephone = otherTelephone;
	}

	public String getPager()
	{
		return pager;
	}

	public void setPager(String pager)
	{
		this.pager = pager;
	}

	public String getPhysicalDeliveryOfficeName()
	{
		return physicalDeliveryOfficeName;
	}

	public void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName)
	{
		this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
	}

	public String getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public String getPostOfficeBox()
	{
		return postOfficeBox;
	}

	public void setPostOfficeBox(String postOfficeBox)
	{
		this.postOfficeBox = postOfficeBox;
	}

	public String getPrimaryGroupID()
	{
		return primaryGroupID;
	}

	public void setPrimaryGroupID(String primaryGroupID)
	{
		this.primaryGroupID = primaryGroupID;
	}

	public String getPwdLastSet()
	{
		return pwdLastSet;
	}

	public void setPwdLastSet(String pwdLastSet)
	{
		this.pwdLastSet = pwdLastSet;
	}

	public String getsAMAccountType()
	{
		return sAMAccountType;
	}

	public void setsAMAccountType(String sAMAccountType)
	{
		this.sAMAccountType = sAMAccountType;
	}

	public String getSt()
	{
		return st;
	}

	public void setSt(String st)
	{
		this.st = st;
	}

	public String getStreetAddress()
	{
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress)
	{
		this.streetAddress = streetAddress;
	}

	public String getTelephoneNumber()
	{
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber)
	{
		this.telephoneNumber = telephoneNumber;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getUserAccountControl()
	{
		return userAccountControl;
	}

	public void setUserAccountControl(String userAccountControl)
	{
		this.userAccountControl = userAccountControl;
	}

	public String getUserPassword()
	{
		return userPassword;
	}

	public void setUserPassword(String userPassword)
	{
		this.userPassword = userPassword;
	}

	public String getUserPrincipalName()
	{
		return userPrincipalName;
	}

	public void setUserPrincipalName(String userPrincipalName)
	{
		this.userPrincipalName = userPrincipalName;
	}

	public String getuSNChanged()
	{
		return uSNChanged;
	}

	public void setuSNChanged(String uSNChanged)
	{
		this.uSNChanged = uSNChanged;
	}

	public String getuSNCreated()
	{
		return uSNCreated;
	}

	public void setuSNCreated(String uSNCreated)
	{
		this.uSNCreated = uSNCreated;
	}

	public String getWhenChanged()
	{
		return whenChanged;
	}

	public void setWhenChanged(String whenChanged)
	{
		this.whenChanged = whenChanged;
	}

	public String getWhenCreated()
	{
		return whenCreated;
	}

	public void setWhenCreated(String whenCreated)
	{
		this.whenCreated = whenCreated;
	}

	public String getwWWHomePage()
	{
		return wWWHomePage;
	}

	public void setwWWHomePage(String wWWHomePage)
	{
		this.wWWHomePage = wWWHomePage;
	}

	public String getdNSHostName()
	{
		return dNSHostName;
	}

	public void setdNSHostName(String dNSHostName)
	{
		this.dNSHostName = dNSHostName;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public void setNodeName(String nodeName)
	{
		this.nodeName = nodeName;
	}

	public void setData(HashMap<String, Object> data)
	{
		this.data = data;
	}
}
