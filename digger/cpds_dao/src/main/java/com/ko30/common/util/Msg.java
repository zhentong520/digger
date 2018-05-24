package com.ko30.common.util;

import java.util.HashMap;
import java.util.Map;

public class Msg {
/**
 * 定义项目中会用到的信息 设计理念: 1.在其它类中, 能返回良好的信息格式: Msg:{code:11001010,text:"手机号格式错误"}
 * 2.在其它类中, 能便捷指定需要的信息: Msg.M11001010(), 没有定义的信息代码不能被指定 3.在其它类中, 利用编辑器提供的功能,
 * 能直接跳到信息定义行, 便于查看信息, 比如: 按住 Ctrl 键, 再单击 Msg.M11001010() 中的 M11001010,
 * 会直接跳到信息定义行 4.定义或编辑一个信息时, 做到尽量简捷直观 5.生成信息的 jsonString 时,
 * 尽量减少对原始信息的字符串处理(性能很重要) 6.同一个接口用到的信息定义在一块, 方便查询
 * 
 * 信息编码约定: 1.格式: "M" + 两位数的模块序号(从 11 开始编号) + 三位数的接口编号(从 001 开始编号) +
 * 三位数的信息流水号(从 010 开始, 初始步增为 10) 2.M0 是一个特殊的信息编码, 代表"请求成功!" 3.信息编码不可以重复,
 * 内容允许重复, 比如注册接口有一个用户名参数, 登录接口也有一个用户名参数, 用户名都不能为空, 可以单独定义两个信息编码
 * 4.可以在两个信息编码之间通过扩充信息位数来插入新的信息
 * 
 * @author JGH
 *
 */
    /** 适用所有模块 */
    public static Map<String, Object> M0(){return _fmtMsg("请求成功");} 
    public static Map<String, Object> M101(){return _fmtMsg("请先登录");}
    public static Map<String, Object> M105(){return _fmtMsg("数据库请求失败");}
    public static Map<String, Object> M106(){return _fmtMsg("在 session 对象中, userId 代表的用户已不存在");}
    public static Map<String, Object> M107(){return _fmtMsg("没有数据");}
    public static Map<String, Object> M108(){return _fmtMsg("存储过程执行发生错误, 数据更新已回滚");}
    public static Map<String, Object> M109(){return _fmtMsg("查询的关键词不能为空");}
    
    /** 权限模块.注册.获取短信验证码 */
    public static Map<String, Object> M11001010(){return _fmtMsg("手机号格式错误");} 
    public static Map<String, Object> M11001020(){return _fmtMsg("该手机号已经被注册");}
    public static Map<String, Object> M11001030(){return _fmtMsg("发送短信到指定的手机号失败");}
    public static Map<String, Object> M11001040(){return _fmtMsg("注册失败");}  
    
    /** 权限模块.注册 */
    public static Map<String, Object> M11002010(){return _fmtMsg("手机号格式错误");} 
    public static Map<String, Object> M11002020(){return _fmtMsg("该手机号已经被注册");}
    public static Map<String, Object> M11002030(){return _fmtMsg("密码长度必须大于或等于 6 位");}
    public static Map<String, Object> M11002040(){return _fmtMsg("验证码不对");}
    
    /** 权限模块.注册.完善资料 */
    public static Map<String, Object> M11003010(){return _fmtMsg("昵称不能为空");} 
    public static Map<String, Object> M11003020(){return _fmtMsg("出生日期错误");} 
    public static Map<String, Object> M11003030(){return _fmtMsg("上传头像失败");}
    
    /** 权限模块.登录 */
    public static Map<String, Object> M11004010(){return _fmtMsg("手机号格式错误");}
    public static Map<String, Object> M11004020(){return _fmtMsg("账号或密码不对");}
    public static Map<String, Object> M11004021(){return _fmtMsg("微信 openId 不对");}
    public static Map<String, Object> M11004022(){return _fmtMsg("QQ openId 不对");}
    public static Map<String, Object> M11004029(){return _fmtMsg("指定的登录方式不被系统支持");}    
    public static Map<String, Object> M11004030(){return _fmtMsg("从融云获取 token 失败");} 
    public static Map<String, Object> M11004040(){return _fmtMsg("抱歉,您的权限不足");}     
    public static Map<String, Object> M11004050(){return _fmtMsg("登录失败");}
    
    /** 权限模块.修改头像 */
    public static Map<String, Object> M11005010(){return _fmtMsg("头像路径不能为空");}    
    public static Map<String, Object> M11005020(){return _fmtMsg("头像路径不能超过 200 个字符");}
    public static Map<String, Object> M11005030(){return _fmtMsg("图片格式不支持上传");}
    public static Map<String, Object> M11005040(){return _fmtMsg("头像刷新失败");}
    
    /** 权限模块.修改昵称 */
    public static Map<String, Object> M11006010(){return _fmtMsg("不能修改内置管理用户的昵称");}
    public static Map<String, Object> M11006020(){return _fmtMsg("昵称不能为空");} 
    public static Map<String, Object> M11006030(){return _fmtMsg("昵称不能超过 30 个字符");}
    public static Map<String, Object> M11006040(){return _fmtMsg("昵称刷新失败");}
    
    /** 权限模块.修改角色 */
    public static Map<String, Object> M11007010(){return _fmtMsg("只有用管理用户登录后才可以修改其它用户的角属性");}
    public static Map<String, Object> M11007015(){return _fmtMsg("指定的用户不存在");}
    public static Map<String, Object> M11007020(){return _fmtMsg("不能修改登录用户自己的角色属性");}
    public static Map<String, Object> M11007030(){return _fmtMsg("不能修改内置管理用户 admin 角色属性");}
    public static Map<String, Object> M11007040(){return _fmtMsg("角色不能为空");}
    public static Map<String, Object> M11007050(){return _fmtMsg("角色属性值必须为 [普通用户/达人用户/管理用户] 之一");}
    
    /** 权限模块.修改密码 */
    public static Map<String, Object> M11008010(){return _fmtMsg("手机号格式错误");}
    public static Map<String, Object> M11008020(){return _fmtMsg("密码长度必须大于或等于 6 位");}
    public static Map<String, Object> M11008030(){return _fmtMsg("密码确认错误");}
    public static Map<String, Object> M11008031(){return _fmtMsg("密码输入错误");}
    public static Map<String, Object> M11008032(){return _fmtMsg("密码错误锁定");}
    public static Map<String, Object> M11008033(){return _fmtMsg("用户禁止登陆");}
    public static Map<String, Object> M11008040(){return _fmtMsg("验证码不对");}
    public static Map<String, Object> M11008050(){return _fmtMsg("该手机号没有注册");}
    
    /** 权限模块.修改密码.获取短信验证码 */
    public static Map<String, Object> M11009010(){return _fmtMsg("手机号格式错误");} 
    public static Map<String, Object> M11009020(){return _fmtMsg("该手机号没有注册");}
    public static Map<String, Object> M11009030(){return _fmtMsg("发送短信到指定的手机号失败");}
    
    /** 权限模块.微信登录 */
    public static Map<String, Object> M110010010(){return _fmtMsg("上传的 openid 或 access_tocken 无效");} 
    public static Map<String, Object> M110010020(){return _fmtMsg("从融云获取 token 失败");}
    public static Map<String, Object> M110010030(){return _fmtMsg("注册失败");}    
    public static Map<String, Object> M110010040(){return _fmtMsg("从微信服务器获取指定 openid 的用户资料失败");}
    
    /** 权限模块.QQ 登录 */
    public static Map<String, Object> M110011010(){return _fmtMsg("从服务端获取 openid 无效");} 
    public static Map<String, Object> M110011020(){return _fmtMsg("从服务端获取 openid 失败");}
    public static Map<String, Object> M110011030(){return _fmtMsg("使用Access Token以及OpenID来访问和修改用户数据失败");}
    public static Map<String, Object> M110011040(){return _fmtMsg("注册失败");}
    public static Map<String, Object> M110011050(){return _fmtMsg("从服务器获取access_Token失败");}
    
    /** 权限模块.绑定账号 */
    public static Map<String, Object> M11012010(){return _fmtMsg("绑定的账号不能为空");}
    public static Map<String, Object> M11012020(){return _fmtMsg("绑定的账号类型必须为 telephone, qq, wx 之一");}
    public static Map<String, Object> M11012030(){return _fmtMsg("参数不能为空");}
    public static Map<String, Object> M11012040(){return _fmtMsg("手机号格式错误");}
    public static Map<String, Object> M11012050(){return _fmtMsg("该手机号已经被注册或被绑定");}
    public static Map<String, Object> M11012060(){return _fmtMsg("密码长度必须大于或等于 6 位");}
    public static Map<String, Object> M11012070(){return _fmtMsg("密码碓认不对");}
    public static Map<String, Object> M11012080(){return _fmtMsg("验证码不对");}
    public static Map<String, Object> M11012090(){return _fmtMsg("qq 服务端未返回任何信息");}
    public static Map<String, Object> M11012100(){return _fmtMsg("qq 服务端返回的信息不符合要求");}
    public static Map<String, Object> M11012110(){return _fmtMsg("该 qq 号已经被注册或被绑定");}
    public static Map<String, Object> M11012120(){return _fmtMsg("上传的 openid 或 access_tocken 无效");}
    
    
    
    
       
    /** 发现模块.增加笔记 */
    public static Map<String, Object> M12001010(){return _fmtMsg("类别输入不正确");} 
    public static Map<String, Object> M12001020(){return _fmtMsg("图片或视频不能全为空");} 
    public static Map<String, Object> M12001030(){return _fmtMsg("文字内容不能全为空");}

    /** 发现模块.查看类别的所有动态 */
    public static Map<String, Object> M12001110(){return _fmtMsg("种类名称不存在");} 
    
    /** 发现模块.笔记删除 */
    public static Map<String, Object> M12001111(){return _fmtMsg("未指定要删除的笔记");} 
    public static Map<String, Object> M12001112(){return _fmtMsg("指定的笔记 ID 无效");} 
    
    /** 发现模块.点赞 */
    public static Map<String, Object> M12002010(){return _fmtMsg("您已赞过");} 
    public static Map<String, Object> M12002020(){return _fmtMsg("您已查看过");}
    public static Map<String, Object> M12002030(){return _fmtMsg("该笔记不存在");}

    /** 发现模块.收藏 */
    public static Map<String, Object> M12002110(){return _fmtMsg("您已收藏过");} 
    public static Map<String, Object> M12002120(){return _fmtMsg("该笔记不存在");}
    public static Map<String, Object> M12002130(){return _fmtMsg("");}

    
    /** 发现模块.动态详情页 */
    public static Map<String, Object> M12003010(){return _fmtMsg("该笔记不存在");}
    
    /** 发现模块.增加笔记评论 */
    public static Map<String, Object> M12004010(){return _fmtMsg("该笔记不存在");}
    public static Map<String, Object> M12004020(){return _fmtMsg("评论不能为空");}

    /** 发现模块.增加笔记回复 */
    public static Map<String, Object> M12004110(){return _fmtMsg("该笔记不存在");}
    public static Map<String, Object> M12004120(){return _fmtMsg("回复不能为空");}  
    public static Map<String, Object> M12004130(){return _fmtMsg("自己不能回复自己");}
    
    /** 发现模块.给评论点赞 */
    public static Map<String, Object> M12005010(){return _fmtMsg("该评论不存在");}
    public static Map<String, Object> M12005020(){return _fmtMsg("该用户已点赞");}
    
    /** 发现模块.查看个人资料 */
    public static Map<String, Object> M12100010(){return _fmtMsg("被查看资料的用户不存在");}
    public static Map<String, Object> M12100020(){return _fmtMsg("指定用户不存在");}
    
    /** 发现模块.查看个人资料.点赞 */
    public static Map<String, Object> M12101010(){return _fmtMsg("被点赞用户不存在");}
    public static Map<String, Object> M12101020(){return _fmtMsg("自己不能赞自己");}
    public static Map<String, Object> M12101030(){return _fmtMsg("不能重复点赞");}

    /** 发现模块.查看个人资料.加关注 */
    public static Map<String, Object> M12102010(){return _fmtMsg("被关注的用户不存在");}
    public static Map<String, Object> M12102020(){return _fmtMsg("不能自己关注自己");}    
    public static Map<String, Object> M12102030(){return _fmtMsg("已经关注了");}
    
    /** 发现模块.查看个人资料.加好友 */
    public static Map<String, Object> M12103010(){return _fmtMsg("在 TbUser 中找不到 friendingUserId 代表的记录");}
    public static Map<String, Object> M12103020(){return _fmtMsg("自己不能加自己为好友");}
    public static Map<String, Object> M12103030(){return _fmtMsg("已经加了好友, 不能重复加好友");}
    public static Map<String, Object> M12103031(){return _fmtMsg("不能自己同意自己发送的加好友请求");}
    public static Map<String, Object> M12101040(){return _fmtMsg("在 TbUser 中找不到 friendingByUserId 代表的记录");}
    public static Map<String, Object> M12101050(){return _fmtMsg("好友表里面不存在参数所指定的记录");}
    
    
    /** 个人设置模块.修改性别 */
    public static Map<String, Object> M16001010(){return _fmtMsg("性别不能为空");}
    public static Map<String, Object> M16001020(){return _fmtMsg("性别属性值必须为 [男/女] 之一");}
    
    /** 个人设置模块.修改地址 */
    public static Map<String, Object> M16002010(){return _fmtMsg("地址不能为空");}
    public static Map<String, Object> M16002020(){return _fmtMsg("地址不能超过 50 个字符");}

    /** 个人设置模块.修改出生日期 */
    public static Map<String, Object> M16003010(){return _fmtMsg("出生日期不能为空");}
    public static Map<String, Object> M16003020(){return _fmtMsg("出生日期格式不正确");}
   
    /** 个人设置模块.修改个性签名 */
    public static Map<String, Object> M16004010(){return _fmtMsg("个性签名不能超过 100 个字符");} 
    
    /** 个人设置模块.刷新当前位置 */
    public static Map<String, Object> M16005010(){return _fmtMsg("城市名称不能超过 50 个字符");} 
    
    /** 自我.商城 */
    public static Map<String, Object> M17005010(){return _fmtMsg("商品名称不为空");}
    public static Map<String, Object> M17005020(){return _fmtMsg("商品图片不为空");}
    public static Map<String, Object> M17005030(){return _fmtMsg("商品价格不为空");}
    public static Map<String, Object> M17005040(){return _fmtMsg("商品品牌不为空");}
    public static Map<String, Object> M17005050(){return _fmtMsg("商品介绍不为空");}
    
    /** 自我.商城详情 */
    public static Map<String, Object> M17005060(){return _fmtMsg("该商品不存在");}
    
    /** 自我   添加收货地址 */
    public static Map<String, Object> M17006010(){return _fmtMsg("收货人不能为空");}
    public static Map<String, Object> M17006020(){return _fmtMsg("手机号不能为空");}
    public static Map<String, Object> M17006030(){return _fmtMsg("收货地址不能为空");}
    public static Map<String, Object> M17006040(){return _fmtMsg("详细地址不能为空");}

    
    /** 自我   修改收货地址 */
    public static Map<String, Object> M17007010(){return _fmtMsg("收货人不能为空");}
    public static Map<String, Object> M17007020(){return _fmtMsg("手机号不能为空");}
    public static Map<String, Object> M17007030(){return _fmtMsg("收货地址不能为空");}
    public static Map<String, Object> M17007040(){return _fmtMsg("详细地址不能为空");}
    
    /** 自我  选择默认地址 */
    public static Map<String, Object> M17008010(){return _fmtMsg("该收货地址不存在");}
    
    /** 自我    收货地址列表 */
    public static Map<String, Object> M17009010(){return _fmtMsg("请添加收货地址");}
    
    
    /** 后台管理.上传商品主要图像 */
    public static Map<String, Object> M31001010(){return _fmtMsg("图片格式不支持上传");}

    /** 后台管理.增加商品 */
    public static Map<String, Object> M31002010(){return _fmtMsg("商品名称不能为空");}
    public static Map<String, Object> M31002020(){return _fmtMsg("不能重复增加同种规格的商品");}
    public static Map<String, Object> M31002030(){return _fmtMsg("商品主要图片不能为空");}
    
    
    /** 视频 */
    public static Map<String, Object> M18001010(){return _fmtMsg("视频不能为空");}
    
    /** 自我.钱包 */
    public static Map<String, Object> M19001010(){return _fmtMsg("支付金额必须大于零");}
    
    /** 自我.vip */
    public static Map<String, Object> M21001010(){return _fmtMsg("vip名称不能为空");}
    public static Map<String, Object> M21001020(){return _fmtMsg("会员价格必须大于零");}
    public static Map<String, Object> M21001030(){return _fmtMsg("会员天数必须大于零");}
    public static Map<String, Object> M21001040(){return _fmtMsg("只有用管理用户登录后才可以添加vip的购买类型");}
    public static Map<String, Object> M21001050(){return _fmtMsg("指定的 vipId 不存在");}
    public static Map<String, Object> M21001060(){return _fmtMsg("生成支付宝订单失败");}
    
    /** 购物车 */
    public static Map<String, Object> M22001001(){return _fmtMsg("指定的 userId 不存在");}
    public static Map<String, Object> M22001002(){return _fmtMsg("指定的 addressId 不存在");}
    public static Map<String, Object> M22001003(){return _fmtMsg("指定的 cartGoodsIds 格式无效");}
    public static Map<String, Object> M22001004(){return _fmtMsg("cartGoodsIds 指定的商品信息不完整，无法结算");}
    public static Map<String, Object> M22001005(){return _fmtMsg("购物车里面待结算的商品, 不能重复");}
    public static Map<String, Object> M22001006(){return _fmtMsg("购物车里面待结算的商品, 库存必须满足要求");}
    public static Map<String, Object> M22001007(){return _fmtMsg("cartGoodsIds 指定的商品信息不完整，无法删除");}
    
    public static Map<String, Object> M22001010(){return _fmtMsg("商品不存在");}
    public static Map<String, Object> M22001011(){return _fmtMsg("购物车中不存在此商品");}
    public static Map<String, Object> M22001012(){return _fmtMsg("商品库存不够");}
    public static Map<String, Object> M22001013(){return _fmtMsg("支付金额小于或等于零");}
    public static Map<String, Object> M22001020(){return _fmtMsg("价钱错误");}
    public static Map<String, Object> M22001030(){return _fmtMsg("购买数量不能小于或等于0");}
    public static Map<String, Object> M22001040(){return _fmtMsg("未指定要删除的商品");}
    public static Map<String, Object> M22001050(){return _fmtMsg("cartGoodsIds 不能为空");}
    public static Map<String, Object> M22001060(){return _fmtMsg("商品已结算");}
    
    public static Map<String, Object> M22001070(){return _fmtMsg("指定的 orderId 不存在");}
    public static Map<String, Object> M22001071(){return _fmtMsg("指定的 orderNo 不存在");}
    public static Map<String, Object> M22001080(){return _fmtMsg("生成支付宝订单失败");}
    
    public static Map<String, Object> M22001210(){return _fmtMsg("XmlString 转换成 Map 对象失败");}
    public static Map<String, Object> M22001220(){return _fmtMsg("无法从 XmlString 提取 prepay_id 字段值");}
    
    /** 聊天室 */
    public static Map<String, Object> M23001020(){return _fmtMsg("聊天室名称不能为空");}
    public static Map<String, Object> M23001030(){return _fmtMsg("融云后台创建聊天室失败");}
    public static Map<String, Object> M23001040(){return _fmtMsg("融云返回结果不符合期望");}
    public static Map<String, Object> M23001050(){return _fmtMsg("无法获取聊天室人数和头像");}
    public static Map<String, Object> M23001060(){return _fmtMsg("指定的聊天室ID不存在");}
    public static Map<String, Object> M23001070(){return _fmtMsg("指定的聊天室ID在融云已过时");}
    public static Map<String, Object> M23001080(){return _fmtMsg("融云聊天室用户在 APP 后台没有发现");}
    

	// 不要修改以下代码====================================================
	private static Map<String, Map<String, Object>> allMsgMap = new HashMap<String, Map<String, Object>>();

	/**
	 * @param rawMsg
	 *            原始信息
	 * @return 返回一个 jsonString, 格式: {code:NNNN,text:XXXX}
	 */
	private static Map<String, Object> _fmtMsg(String rawMsg) {
		// getStackTrace()[0] = getStackTrace
		// getStackTrace()[1] = _fmtMsg
		// getStackTrace()[2] = 当前方法的父方法名
		String msgCode = (Thread.currentThread().getStackTrace()[2]
				.getMethodName()).substring(1);

		if (!allMsgMap.containsKey(msgCode)) {
			Map<String, Object> msgMap = new HashMap<String, Object>();
			msgMap.put("code", Integer.parseInt(msgCode));
			msgMap.put("text", rawMsg);
			allMsgMap.put(msgCode, msgMap);
		}

		return allMsgMap.get(msgCode);
	}
}
