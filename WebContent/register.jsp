<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />
<link rel="stylesheet" href="css/checkPwd.css" type="text/css" />
<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}

.pw-bar {
            background: url(images/pwd-1.png) no-repeat;
            height: 14px;
            overflow: hidden;
            width: 179px;
        }
.pw-bar-on {
            background: url(images/pwd-2.png) no-repeat;
            width: 0px;
            height: 14px;
            position: absolute;
            top: 1px;
            left: 2px;
            transition: width .5s ease-in;
            -moz-transition: width .5s ease-in;
            -webkit-transition: width .5s ease-in;
            -o-transition: width .5s ease-in;
        }

</style>
<script type="text/javascript" src="js/security.js"></script>
<script type="text/javascript">
   function changeIMG(){
	var img1=document.getElementById("img1");
	img1.src="http://localhost:8080/amazon/imageCodeServlet?time"+new Date().getTime();
   }
$(function (){
	//异步校验用户名
	var encrypedPwd;
	$("#username").blur(function(){		
		var username=$(this).val();
	
		$.post("userServlet",{"username":username,"method":"checkUsername"},function(data){
			if(data==2){
				//用户名可以使用
				//alert("用户名可用");
				$("#span1").html("用户名可以使用");
				$("#span1").css("color","green");
				$("#regBut").prop("disabled",false);
			}else if(data==1){
				//用户名不可使用
				$("#span1").html("该用户已注册");
				$("#span1").css("color","red");
				$("#regBut").prop("disabled",true);		
			}else{
				//用户名为空
				$("#span1").html("用户名不能为空");
				$("#span1").css("color","red");
				$("#regBut").prop("disabled",true);	
			}	    					
		})            		
	})
	
	//验证密码强度
	$('#inputPassword3').keyup(function () { 
		var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); 
		var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g"); 
		var enoughRegex = new RegExp("(?=.{6,}).*", "g"); 
	
		if (false == enoughRegex.test($(this).val())) { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass(' pw-defule'); 
			 //密码小于六位的时候，密码强度图片都为灰色 
		} 
		else if (strongRegex.test($(this).val())) { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass(' pw-strong'); 
			 //密码为八位及以上并且字母数字特殊字符三项都包括,强度最强 
		} 
		else if (mediumRegex.test($(this).val())) { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass(' pw-medium'); 
			 //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等 
		} 
		else { 
			$('#level').removeClass('pw-weak'); 
			$('#level').removeClass('pw-medium'); 
			$('#level').removeClass('pw-strong'); 
			$('#level').addClass('pw-weak'); 
			 //如果密码为6为及以下，就算字母、数字、特殊字符三项都包括，强度也是弱的 
		} 
		return true; 
	});
	
	//验证两次密码是否一致
	$("#confirmpwd").keyup(function(){
    		
    		var password=$("#inputPassword3").val();
    		var password1=$("#confirmpwd").val();
    		<!--对比两次输入的密码-->
    		if(password==password1){
    			//两次密码相同
    			$("#span2").html("两次密码相同");
    	        $("#span2").css("color","green");
    	        $("#regBut").prop("disabled",false);
    		}else{
    			//两次密码不相同
    			$("#span2").html("两次密码不相同");
                $("#span2").css("color","red");
                $("#regBut").prop("disabled",true);
    		}	
    	})
    	
    	//再次验证密码是否一致
    	$("#inputEmail3").click(function(){
    		
    		var password=$("#inputPassword3").val();
    		var password1=$("#confirmpwd").val();
    		<!--对比两次输入的密码-->
    		if(password==password1){
    			//两次密码相同
    			$("#span2").html("两次密码相同");
    	        $("#span2").css("color","green");
    	        $("#regBut").prop("disabled",false);
    		}else{
    			//两次密码不相同
    			$("#span2").html("两次密码不相同");
                $("#span2").css("color","red");
                $("#regBut").prop("disabled",true);
    		}	
    	})
    	
    	//禁止输入危险字符串
    	$.fn.replaceOther = function (){
		this.bind("keypress", function (e) {
	        var keyCode = e ? e.which : event.keyCode;
	        if (keyCode == 60 || keyCode == 62) {
	            return false;
	        }
	    });
		//禁止拖放
	    this.bind("dragenter", function () {
	        return false;
	    });
	    this.on("keyup blur", function () {
	        var s = this.value;
	        if (s.indexOf("+") != -1 || s.indexOf("/") != -1 || s.indexOf("\\") != -1 || s.indexOf("?") != -1 || s.indexOf("？") != -1 || s.indexOf("%") != -1 || s.indexOf("#") != -1 || s.indexOf("&") != -1 || s.indexOf("=") != -1 || s.indexOf("(") != -1 || s.indexOf(")") != -1 || s.indexOf("（") != -1 || s.indexOf("）") != -1 || s.indexOf("{") != -1 || s.indexOf("}") != -1 || s.indexOf("\"") != -1 || s.indexOf("<") != -1 || s.indexOf(">") != -1 || s.indexOf("@") != -1 || s.indexOf("!") != -1 || s.indexOf("！") != -1 || s.indexOf("$") != -1 || s.indexOf(".") != -1 || s.indexOf(",") != -1 || s.indexOf("，") != -1 || s.indexOf("、") != -1 || s.indexOf(":") != -1 || s.indexOf("：") != -1 || s.indexOf(";") != -1 || s.indexOf("；") != -1 || s.indexOf("￥") != -1 || s.indexOf("*") != -1 || s.indexOf("~") != -1 || s.indexOf("`") != -1 || s.indexOf("-") != -1 || s.indexOf("——") != -1 || s.indexOf("_") != -1 || s.indexOf("^") != -1 || s.indexOf("“") != -1 || s.indexOf("”") != -1 || s.indexOf("‘") != -1 || s.indexOf("’") != -1 || s.indexOf("……") != -1 || s.indexOf("[") != -1 || s.indexOf("]") != -1 || s.indexOf("【") != -1 || s.indexOf("】") != -1){
	        	this.value = s.replace(new RegExp("[\\+,\\/,\\\\,\\?,\\？,\\%,\\#,\\&,\\=,\\(,\\),\\（,\\）,\\{,\\},\\',\\\",\\<,\\>,\\@,\\!,\\！,\\$,\\.,\\，,\\、,\\:,\\：,\\;,\\；,\\￥,\\*,\\~,\\`,\\-,\\——,\\_,\\^,\\“,\\”,\\‘,\\’,\\……,\\【,\\】,\\[,\\],\\,]", "gm"), ""); 
	        }
	            
	    });
	};
	
		//绑定输入框

	$("#usercaption").replaceOther();
    
	   //密码RSA加密
	$("#confirmpwd").blur(function(){
    		var password=$(this).val();
    	 /* RSAUtils.setMaxDigits(200);  		
    		var key = new RSAUtils.getKeyPair("${publicKeyExponent}", "", "${publicKeyModulus}");
    
    		var encrypedPwd = RSAUtils.encryptedString(key,password.split("").reverse().join(""));*/
    		$.post("userServlet",{"password":password,"method":"encode"},function(data){
    			var str=data.split(",");
    		   RSAUtils.setMaxDigits(200);
    		   var key = new RSAUtils.getKeyPair(str[0], "", str[1]);
    		   encrypedPwd = RSAUtils.encryptedString(key,password.split("").reverse().join(""));
    		   alert(encrypedPwd);
    		})
    		
    	})
    	
    	
    	//异步传入加密的密码
    	 $("#btn1").click(function(){
            var encode=encrypedPwd;
            
    		$.post("userServlet",{"encode":encode,"async": false,"method":"encodepassword"},function(data){
    		    
    			
    		})
    	})
    	
	
})
</script>
</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container"
		style="width: 100%; background: url('image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<form class="form-horizontal" style="margin-top: 5px;" action="${pageContext.request.contextPath }/userServlet" method="post">
				<input type="hidden" name="method" value="regist" /> 
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username" name="username" onblur="checkUsername(this)"
								placeholder="请输入用户名" required>
						</div><span id="span1"></span>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control  input-style" id="inputPassword3" name="passwordaa"
								placeholder="请输入密码" required>			
						</div>
						    <div id="level" class="pw-strength" style="display: inline-block;">
                              <div class="pw-bar" style="display: inline-block;"></div>
                              <div class="pw-bar-on" style="display: inline-block;"></div>
                              <div class="pw-txt" style="display: inline-block;">
                              <span>弱</span> <span>中</span> <span>强</span>
                              </div>
                          </div>	
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="confirmpwd" name="confirmpwd"
								placeholder="请输入确认密码" >
						</div><span id="span2"></span>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="inputEmail3" name="email"
								placeholder="Email" required>
						</div>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="usercaption" name="name"
								placeholder="请输入姓名" required>
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline"> <input type="radio"
								name="inlineRadioOptions" id="inlineRadio1" value="男" selected>
								男
							</label> 
							<label class="radio-inline"> <input type="radio"
								name="inlineRadioOptions" id="inlineRadio2" value="女">
								女
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="date" class="form-control" name="birthday" required>
						</div>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="quickmark" required>

						</div>
							<div class="col-sm-3">
								<img src="http://localhost:8080/amazon/imageCodeServlet" onclick="changeIMG()" id="img1" />
								<a href="javascript:void(0)" onclick="changeIMG()">换一张</a>
							</div>
							${MSG}
					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit" id="btn1"
								style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>




