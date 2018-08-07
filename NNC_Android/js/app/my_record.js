	var weightFlag,weightFlag1;
	var mask=mui.createMask();//遮罩层
	$('.testpage-img').click(function() {
		var $obj = $(this);
		$obj.addClass('active')
			.parent('.testpage-list-box').siblings()
			.find('.testpage-img').removeClass('active');
	});

	function openBox(obj) {
		var objClass = $(obj).attr("id");
		$(".mui-backdrop,." + objClass + "").show();
		$("li." + objClass).siblings().hide();
	}
	var subDate;
	var subYear;
	var subMouth;
	var subDay;
	var uid;
	mui.init();
	//var _self, _next, timer;
	mui.plusReady(function() {
		Internet_connection();
		//获取本地数据
		uid = localStorage.getItem('NNC_userInfoId');
		window.addEventListener('close', function(e) { //执行方法
			closeIframe2();
		});
		window.addEventListener('close_save_breakfast', function(e) { //执行方法
			getBreakfastPhoto();
		});
		window.addEventListener('close_save_lunch', function(e) { //执行方法
			getLunchPhoto();
		});
		window.addEventListener('close_save_dinner', function(e) { //执行方法
			getDinnerPhoto();
		});
		//mui日历插件预加载
		var d = new Date();
		subDate = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
		subYear = d.getFullYear();
		subMouth = d.getMonth() + 1;
		subDay = d.getDate();
		isLogin();
		//周日历插件
		var color;
		var cells = document.getElementById('monitor').getElementsByTagName('td');
		var clen = cells.length;
		var currentFirstDate;
		var year;
		var mouth;
		var setDates = function(date) {
			year = date.getFullYear();
			mouth = (date.getMonth() + 1);
			var week = date.getDay();
			date = addDate(date, week * -1);
			currentFirstDate = new Date(date);
			for(var i = 0; i < clen; i++) {
				cells[i].innerHTML = formatDate(i == 0 ? date : addDate(date, 1));
				if(color == 1) {
					$('#monitor td').removeAttr("style");
					cells[i].style.backgroundRepeat = "no-repeat";
					cells[i].style.backgroundPositionX = '48%';
					cells[i].style.backgroundImage = 'url(../../image/icon/calendar_white.png)';
					cells[i].style.color = '#17A984';
				} else {
					cells[i].style.backgroundColor = '';
					cells[i].style.color = '#FFFFFF';
				}
			}
			h('#ym').html(year + '年' + mouth + '月');
		};
		var formatDate = function(date) {
			var day2 = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
			if(subDate == day2) {
				day = date.getDate();
				color = 1;
			} else {
				day = date.getDate();
				color = 0;
			}
			var week = '(' + ['日', '一', '二', '三', '四', '五', '六'][date.getDay()] + ')';
			return day;
		};
		$(function() {
			$('#monitor td').click(function() {
				if(loginStatus() == true) {
					$('#monitor td').removeAttr("style");
					$(this).attr('style', 'color:#17A984;background-image:url(../../image/icon/calendar_white.png);background-repeat: no-repeat;background-position-x: 48%;');
					var clickDay = $(this).html();
					var firstDay = $("#first").html(); //一周显示第一个td的值
					var lastDay = $("#last").html(); //一周显示最后一个td的值
					var clickYear;
					var clickMouth;
					if(Number(clickDay) > Number(subDay)) {
						if(Number(clickDay) - Number(subDay) > 7) {
							if(Number(clickDay) > Number(lastDay)) { //为上一个月 else为本月
								if(Number(subMouth) == 1) {
									clickYear = Number(subYear) - 1;
									clickMouth = 12;
									h('#ym').html(clickYear + '年' + clickMouth + '月');
								} else {
									clickYear = Number(subYear);
									clickMouth = Number(subMouth) - 1;
									h('#ym').html(clickYear + '年' + clickMouth + '月');
								}
							} else {
								clickYear = Number(subYear);
								clickMouth = Number(subMouth);
								h('#ym').html(clickYear + '年' + clickMouth + '月');
							}
						} else {
							clickYear = Number(subYear);
							clickMouth = Number(subMouth);
							h('#ym').html(clickYear + '年' + clickMouth + '月');
						}
					} else {
						if(Number(subDay) - Number(clickDay) > 7) {
							if(Number(clickDay) < Number(firstDay)) { //为下一个月else为本月
								if(Number(subMouth) == 12) {
									clickYear = Number(subYear) + 1;
									clickMouth = 1;
									h('#ym').html(clickYear + '年' + clickMouth + '月');
								} else {
									clickYear = Number(subYear);
									clickMouth = Number(subMouth) + 1;
									h('#ym').html(clickYear + '年' + clickMouth + '月');
								}
							} else {
								clickYear = Number(subYear);
								clickMouth = Number(subMouth);
								h('#ym').html(clickYear + '年' + clickMouth + '月');
							}
						} else {
							clickYear = Number(subYear);
							clickMouth = Number(subMouth);
							h('#ym').html(clickYear + '年' + clickMouth + '月');
						}
					}
					var d = clickYear + '-' + clickMouth + '-' + clickDay;
					subDate = d;
					getRecord(d, uid);
				}
			});
		});
		var addDate = function(date, n) {
			date.setDate(date.getDate() + n);
			return date;
		};
		setDates(new Date());

		document.getElementById("time").addEventListener('tap', function() {
			if(loginStatus() == true) {
				var dDate = new Date();
				var minDate = new Date();
				minDate.setFullYear(1999, 0, 1);
				var maxDate = new Date();
				maxDate.setFullYear(2030, 11, 31);
				plus.nativeUI.pickDate(function(e) {
					var d = e.date;
					subDate = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
					subYear = d.getFullYear();
					subMouth = d.getMonth() + 1;
					subDay = d.getDate();
					h('#time').html(subDate);
					//选择日期后查询该日期数据展示
					getRecord(subDate, uid);
					setDates(d);
				}, {
					title: "请选择日期",
					date: dDate,
					minDate: minDate,
					maxDate: maxDate
				});
			}
			getBreakfastPhoto();
			getLunchPhoto();
			getDinnerPhoto();
		});
		document.getElementById("left").addEventListener('tap', function() {
			if(loginStatus() == true) {
				var d= new Date(Date.parse(subDate.replace(/-/g,  "/")));
				d.setDate(d.getDate()-7); 
		        var m=d.getMonth()+1; 
				subDate=d.getFullYear()+'-'+m+'-'+d.getDate();
				h('#time').html(subDate);
				//选择日期后查询该日期数据展示
				getRecord(subDate, uid);
				setDates(d);
				getBreakfastPhoto();
				getLunchPhoto();
				getDinnerPhoto();
			}
		});
		document.getElementById("right").addEventListener('tap', function() {
			if(loginStatus() == true) {
				var d= new Date(Date.parse(subDate.replace(/-/g,  "/")));
				d.setDate(d.getDate()+7); 
		        var m=d.getMonth()+1; 
				subDate=d.getFullYear()+'-'+m+'-'+d.getDate();
				h('#time').html(subDate);
				//选择日期后查询该日期数据展示
				getRecord(subDate, uid);
				setDates(d);
				getBreakfastPhoto();
				getLunchPhoto();
				getDinnerPhoto();
			}
		});
		
		h('#food').tap(function() {
			if(loginStatus() == true) {
				$.ajax({
					type: "POST",
					data: {
						'userInfoId': uid,
						'date': subDate
					},
					url: URL + 'app_rest_ful/select_user_weekly_recommend',
					dataType: 'json',
					success: function(msg) {
						if(msg.success == true) {
							if(msg.data.menu != null) {
								mui.alert(msg.data.menu, '本周饮食推荐');
							} else {
								mui.alert("营养师还未对您这周的饮食进行推荐哦！", '本周饮食推荐');
							}

						} else {
							mui.alert("营养师还未对您这周的饮食进行推荐哦！", '本周饮食推荐');
						}
					}
				});
			}

		});
		h('#sport').tap(function() {
			if(loginStatus() == true) {
				$.ajax({
					type: "POST",
					data: {
						'userInfoId': uid,
						'date': subDate
					},
					url: URL + 'app_rest_ful/select_user_weekly_recommend',
					dataType: 'json',
					success: function(msg) {
						if(msg.success == true) {
							if(msg.data.sport != null) {
								mui.alert(msg.data.sport, '本周运动推荐');
							} else {
								mui.alert("营养师还未对您这周的运动进行推荐哦！", '本周运动推荐');
							}

						} else {
							mui.alert("营养师还未对您这周的运动进行推荐哦！", '本周运动推荐');
						}
					}
				});
			}
		});
		//早餐添加跳转
		h('#breakfast').tap(function() {
			if(loginStatus() == true) {
				if(checkDate(subDate) == true) {
					localStorage.setItem('Submit_date', subDate + '');
//					$('.mui-backdrop,.breakfast-box').show();
//					$('.lunch-box,.dinner-box,.msgbox-sports').hide();
//					var bf = $("#breakfast_info").text();
//					bf = filter(bf);
//					breakfast_info_submit.window.clearBox(bf);
					mui.openWindow({
						url : '../../page/my_record/breakfast_info_submit.html',
						id  : 'breakfast_info_submit.html'
					});
				}
			};
		});
		//午餐添加跳转
		h('#lunch').tap(function() {
			if(loginStatus() == true) {
				if(checkDate(subDate) == true) {
					localStorage.setItem('Submit_date', subDate + '');
//					$('.mui-backdrop,.lunch-box').show();
//					$('.breakfast-box,.dinner-box,.msgbox-sports').hide();
//					var lf = $("#lunch_info").text();
//					lf = filter(lf);
//					lunch_info_submit.window.clearBox(lf);
					mui.openWindow({
						url : '../../page/my_record/lunch_info_submit.html',
						id  : 'lunch_info_submit.html'
					});
				}
			}
		});
		//晚餐添加跳转
		h('#dinner').tap(function() {
			if(loginStatus() == true) {
				if(checkDate(subDate) == true) {
					localStorage.setItem('Submit_date', subDate + '');
//					$('.mui-backdrop,.dinner-box').show();
//					$('.breakfast-box,.lunch-box,.msgbox-sports').hide();
//					var df = $("#dinner_info").text();
//					df = filter(df);
//					dinner_info_submit.window.clearBox(df);
					mui.openWindow({
						url : '../../page/my_record/dinner_info_submit.html',
						id  : 'dinner_info_submit.html'
					});
				}
			};
		});
		h('#msgbox-sports').tap(function() {
			if(loginStatus() == true) {
				if(checkDate(subDate) == true) {
					$('.mui-backdrop,.msgbox-sports').show();
					$('.breakfast-box,.lunch-box,.dinner-box').hide();
				}
			}; 
		});

		h('#msgbox-weight').tap(function() {
			if(loginStatus() == true) {
				if(checkDate(subDate) == true) { 
					weightFlag = 1; 
					var html = "<iframe name='my_record_weight' src='../../page/my_record/weight_new.html'></iframe>";
					layerOpen({
						"title": "体重",
						"content": html,
						"btn": ["取消", "保存"],
						"style": {
							"title": "width: 100%;height: 2.4rem;border-bottom: 1px solid #d1d1d1;line-height: 2.4rem;text-align: center;padding: 0;",
							"content": "width:100%;height: 16.26666667rem;overflow:hidden;",
							"btn": "border-radius: 0px 0px 8px 8px;"
						},
						"event": [null, function() {
							var weight = my_record_weight.window.getweigrh(); 
							if(weight == '') { 
								mui.toast("请选择内容");
							} else {
								$.ajax({
									type: "POST",
									data: {
										'weight': weight,
										'date': subDate,
										'userInfoId': uid
									},
									url: URL + 'app_rest_ful/user_info_data_weight_save',
									dataType: 'json',
									beforeSend: function() {
								        plus.nativeUI.showWaiting(' ');
								        mask.show();//显示遮罩层
								       
								    },
									success: function(msg) {
										if(msg.success == true) {
											mui.toast('提交成功');
											$('#weight_info').text(weight + "kg");
											var data_analysis = plus.webview.getWebviewById("data_analysis.html");
											data_analysis.evalJS("data_analysis();");
										}
										plus.nativeUI.closeWaiting();
								        mask.close();//关闭遮罩层 
									},
									error: function() {
										alert('失败了');
									}
								});
							}
						}]
					});
				}
			}
		});
		//舒适度提交方法
		h('#msgbox-comfort').tap(function() {
			if(loginStatus() == true) {
				if(checkDate(subDate) == true) {
					var html = "<iframe name='my_record_star' src='../../page/my_record/star.html'></iframe>";
					layerOpen({
						"title": "舒适度",
						"content": html,
						"btn": ["取消", "保存"],
						"style": {
							"content": "width:100%;height: 14.14rem;padding-top:5rem;overflow:hidden;",
							"btn": "border-radius: 0px 0px 8px 8px;"
						},
						"event": [null, function() {
							var comfort = my_record_star.window.getStar();
							if(comfort == '') {
								mui.toast("请选择内容");
							} else {
								$.ajax({
									type: "POST",
									data: {
										'comfortLevel': comfort,
										'date': subDate,
										'userInfoId': uid
									},
									url: URL + 'app_rest_ful/user_info_data_comfortLevel_save',
									dataType: 'json',
									beforeSend: function() {
		        						plus.nativeUI.showWaiting('');
								        mask.show();//显示遮罩层
								    },
									success: function(msg) {
										if(msg.success == true) {
											mui.toast('提交成功');
											var html = '';
											for(var a = 0; a < comfort; a++) {
												html += '<img src="../../image/star.png" style="width: 15px;" />';
											}
											h('#comfort_info').html(html);
										} else {
											mui.toast('提交失败');
										}
										plus.nativeUI.closeWaiting();
								        mask.close();//关闭遮罩层
									}
								});
							}
						}]
					});
				}
			}
		});

		//运动提交方法
		h('#sport_today').tap(function() {
			if(loginStatus() == true) {
				var sport_today = $("#sport_val").val();
				sport_today = filter(sport_today);
				h('#sport_today_info').html(sport_today);
				$('.mui-backdrop,.msgbox-sports').hide(200);
				$.ajax({
					type: "POST",
					data: {
						'exercise': sport_today,
						'date': subDate,
						'userInfoId': uid
					},
					url: URL + 'app_rest_ful/user_info_data_exercise_save',
					dataType: 'json',
					beforeSend: function() {
				        plus.nativeUI.showWaiting('');
				        mask.show();//显示遮罩层
				    },
					success: function(msg) {
						if(msg.success == true) {
							h('#not_sport').hide();
							mui.toast('提交成功');
						}
						plus.nativeUI.closeWaiting();
						mask.close();//关闭遮罩层
					}
				});
			}
		});
		//饮水量点击+提交方法
		h('#msgbox-water').tap(function() {
			if(loginStatus() == true) {
				if(checkDate(subDate) == true) {
					var _water = $('#drink_info').text().substring(0, $('#drink_info').text().indexOf('ml'));
					localStorage.setItem('drankWater', _water);
					var html = "<iframe name='my_record_water' src='../../page/my_record/water.html'></iframe>";
					layerOpen({
						"title": "饮水量",
						"content": html,
						"btn": ["取消", "保存"],
						"style": {
							"title": "width: 100%;height: 2.4rem;border-bottom: 1px solid #d1d1d1;line-height: 2.4rem;text-align: center;padding: 0;",
							"content": "width:100%;height: 14.4rem;overflow:hidden;",
							"btn": "border-radius: 0px 0px 8px 8px;"
						},
						"event": [null, function() {
							var drink = my_record_water.window.getWater();
							if(drink == '') {
								mui.toast("请选择内容");
							} else {
								$.ajax({
									type: "POST",
									data: {
										'drankWater': drink,
										'date': subDate,
										'userInfoId': uid
									},
									url: URL + 'app_rest_ful/user_info_data_drankWater_save',
									dataType: 'json',
									beforeSend: function() {
								        plus.nativeUI.showWaiting('');
								        mask.show();//显示遮罩层
								    },
									success: function(msg) {
										if(msg.success == true) {
											mui.toast('提交成功');
											h('#drink_info').html(drink + "ml");
										}
										plus.nativeUI.closeWaiting();
								        mask.close();//关闭遮罩层
									}
								});
							}
						}]
					});
				}
			}
		});

		//试纸点击+提交方法

		h('#msgbox-testpage').tap(function() {
			if(loginStatus() == true) {
				if(checkDate(subDate) == true) {
					var html = "<iframe name='my_record_testPage' src='../../page/my_record/testPage.html'></iframe>";
					layerOpen({
						"title": "试纸",
						"content": html,
						"btn": ["取消", "保存"],
						"style": {
							"title": "width: 100%;height: 2.4rem;border-bottom: 1px solid #d1d1d1;line-height: 2.4rem;text-align: center;padding: 0;",
							"content": "width:100%;height: 12.4rem;padding-top:3rem;overflow:hidden;",
							"btn": "border-radius: 0px 0px 8px 8px;"
						},
						"event": [null, function() {
							var test_paper = my_record_testPage.window.getTestPage();
							if(test_paper == '') {
								mui.toast("请选择内容");
							} else {
								$.ajax({
									type: "POST",
									data: {
										'testPaperValue': test_paper,
										'date': subDate,
										'userInfoId': uid
									},
									url: URL + 'app_rest_ful/user_info_data_testPaperValue_save',
									dataType: 'json',
									beforeSend: function() {
								        plus.nativeUI.showWaiting('');
								        mask.show();//显示遮罩层
								    },
									success: function(msg) {
										if(msg.success == true) {
											mui.toast('提交成功');
											if(test_paper == 1) {
												h('#test_paper_info').html("-/阴性");
											} else if(test_paper == 2) {
												h('#test_paper_info').html("±/5");
											} else if(test_paper == 3) {
												h('#test_paper_info').html("+/15");
											} else if(test_paper == 4) {
												h('#test_paper_info').html("++/40");
											} else if(test_paper == 5) {
												h('#test_paper_info').html("+++/80");
											} else {
												h('#test_paper_info').html("++++/160");
											}
										} else {
											mui.toast('提交失败');
										}
										plus.nativeUI.closeWaiting();
								        mask.close();//关闭遮罩层
									}
								});
							}
						}]
					});
				}
			}
		});

		h('#share').tap(function() {
			var actionbuttons = [{
				title: "微信好友"
			}, {
				title: "朋友圈"
			}, {
				title: "QQ好友"
			}, {
				title: "微博"
			}];
			var actionstyle = {
				title: "请选择分享类型",
				cancel: "取消",
				buttons: actionbuttons
			};
			plus.nativeUI.actionSheet(actionstyle, function(e) { //调用分享弹框方法
				if(e.index) {
					shareNow(e.index);
				} //调用分享方法
			});
		});
	});

	//体重相关方法
	function setWeight(){
		var weight = $("#weight_info").text();
		weight = weight.substring(0,weight.indexOf("kg"));
		return weight; 
	}

	function getRecord(date, uid) {
        plus.nativeUI.showWaiting('');
      //  alert(1);
        $(".bg-nc").show();
        mask.show();//显示遮罩层
	//	 alert(2);       
		$('.recTxt').text('');
		$('div.row.image-list').empty();
		h('#breakfast_info').html('');
		h("#breakfast_val").val('');
		h('#lunch_info').html('');
		h("#lunch_val").val('');
		h('#dinner_info').html('');
		h("#dinner_val").val('');
		h('#sport_today_info').html('');
		h("#sport_val").val('');
		h('#drink_info').html('');
		h('#comfort_info').html('');
		h('#test_paper_info').html('');
		h('#weight_info').html('');
		h('#comments').html('');
		
		$.ajax({
			type: "POST",
			data: {
				'date': date,
				'userInfoId': uid
			},
			url: URL + 'app_rest_ful/find_user_info_data',
			dataType: 'json',
			success: function(msg) {
				if(msg.success == true) {
					if(msg.data.breakfast != null) {
						h('#breakfast_info').html(msg.data.breakfast);
						h("#breakfast_val").val(msg.data.breakfast);
					} else {
						h('#breakfast_info').html('');
						h("#breakfast_val").val('');
					}
					if(msg.data.lunch != null) {
						h('#lunch_info').html(msg.data.lunch);
						h("#lunch_val").val(msg.data.lunch);
					} else {
						h('#lunch_info').html('');
						h("#lunch_val").val('');
					}
					if(msg.data.dinner != null) {
						h('#dinner_info').html(msg.data.dinner);
						h("#dinner_val").val(msg.data.dinner);
					} else {
						h('#dinner_info').html('');
						h("#dinner_val").val('');
					}
					if(msg.data.exercise != null) {
						h('#sport_today_info').html(msg.data.exercise);
						h("#sport_val").val(msg.data.exercise);
						h('#not_sport').hide();
					} else {
						h('#not_sport').show();
						h('#sport_today_info').html('');
						h("#sport_val").val('');
					}
					if(msg.data.drankWater != null) {
						localStorage.setItem('drankWater', msg.data.drankWater);
						h('#drink_info').html(msg.data.drankWater + 'ml');
						h("#r").val(msg.data.drankWater);
					} else {
						h('#drink_info').html('');
						h("#r").val(1200);
					}
					if(msg.data.comfortLevel != null) {
						var html = '';
						for(var a = 0; a < msg.data.comfortLevel; a++) {
							html += '<img src="../../image/star.png" style="width: 15px;" />';
						}
						h('#comfort_info').html(html);
					} else {
						h('#comfort_info').html('');
					}
					if(msg.data.testPaperValue != null) {
						var test_paper = msg.data.testPaperValue;
						if(test_paper == 1) {
							h('#test_paper_info').html("-/阴性");
						} else if(test_paper == 2) {
							h('#test_paper_info').html("±/5");
						} else if(test_paper == 3) {
							h('#test_paper_info').html("+/15");
						} else if(test_paper == 4) {
							h('#test_paper_info').html("++/40");
						} else if(test_paper == 5) {
							h('#test_paper_info').html("+++/80");
						} else {
							h('#test_paper_info').html("++++/160");
						}
					} else {
						h('#test_paper_info').html('');
					}
					if(msg.data.weight != null) {
						h('#weight_info').html(msg.data.weight + 'kg');
					} else {
						h('#weight_info').html('');
					}
					h('#comments').html(msg.data.comments);
					h("#weight_day").html(msg.message);
					plus.nativeUI.closeWaiting();
					mask.close();//关闭遮罩层
					$(".bg-nc").hide();
				}
			}
		});
//		getBreakfastPhoto();
//		getLunchPhoto();
//		getDinnerPhoto();
	}
	$("#sport_today_close").click(function() {
		$(".mui-backdrop,.mui-backdrop>li").hide(200);
	})

	function isLogin() {
		//判断登录状态
		uid = localStorage.getItem('NNC_userInfoId');
		var role = localStorage.getItem('role');
		if(uid == null) {
			h("#weight_day").html("--");
		} else {
			if(role != '2') {
				getRecord(subDate, uid);
			}
		}
	}

	function getBreakfastPhoto() {
		$("#breakfast_show_info").empty();
		$(".image-item").remove("#breakfast_show_photo_id");
		uid = localStorage.getItem('NNC_userInfoId');
		//	$('#breakfast_li').attr('style', 'height:80px;');
		$.ajax({
			type: "POST",
			data: {
				'date': subDate,
				'userInfoId': uid
			},
			url: URL + 'app_rest_ful/find_user_info_breakfast',
			dataType: 'json',
			beforeSend: function() {
		        plus.nativeUI.showWaiting('');
		        mask.show();//显示遮罩层
		    },
			success: function(msg) {
				if(msg.success == true) {
					$('#breakfast_li').attr('style', 'height:auto;');
					$('.breakfast').css({
						'height': '3.32rem',
						'line-height': '3.32rem'
					});
					for(var i = 0; i < msg.data.length; i++) {
						if(i == 0) {
							h('#breakfast_info').html(msg.data[i]);
						} else {
							var src = msg.data[i];
							var html = '';
							html += '<div class="image-item">' +
								'<img src="' + src + '" data-preview-src="" data-preview-group="1" />' +
								'</div>';
							$("#breakfast_show_info").append(html).show();
						}
					}
					$(".mui-backdrop,.mui-backdrop li").hide();

				}
				plus.nativeUI.closeWaiting();
				mask.close();//关闭遮罩层
				$(".bg-nc").hide();
			}
		});
		closeIframe2();
	}

	function getLunchPhoto() {
		$("#lunch_show_info").empty();
		$(".image-item").remove("#lunch_show_photo_id");
		uid = localStorage.getItem('NNC_userInfoId');
		$.ajax({
			type: "POST",
			data: {
				'date': subDate,
				'userInfoId': uid
			},
			url: URL + 'app_rest_ful/find_user_info_lunch',
			dataType: 'json',
			beforeSend: function() {
		        plus.nativeUI.showWaiting('');
		        mask.show();//显示遮罩层
		    },
			success: function(msg) {
				//				console.log(JSON.stringify(msg));  
				$('#lunch_li').attr('style', 'height:auto;');
				$('.lunch').css({
					'height': '3.32rem',
					'line-height': '3.32rem'
				});
				if(msg.success == true) {
					for(var i = 0; i < msg.data.length; i++) {
						if(i == 0) {
							h('#lunch_info').html(msg.data[i]);
						} else {
							var src = msg.data[i];
							var html = '';
							html += '<div class="image-item">' +
								'<img src="' + src + '" data-preview-src="" data-preview-group="2" />' +
								'</div>';
							$("#lunch_show_info").append(html).show();
						}
					}
					$(".mui-backdrop,.mui-backdrop li").hide(200);
				}
				plus.nativeUI.closeWaiting();
				mask.close();//关闭遮罩层
				$(".bg-nc").hide();
			}
		});
	}

	function getDinnerPhoto() {
		$("#dinner_show_info").empty();
		uid = localStorage.getItem('NNC_userInfoId');
		$.ajax({
			type: "POST",
			data: {
				'date': subDate,
				'userInfoId': uid
			},
			beforeSend: function() {
		        plus.nativeUI.showWaiting('');
		        mask.show();//显示遮罩层
		    },
			url: URL + 'app_rest_ful/find_user_info_dinner',
			dataType: 'json',
			success: function(msg) {
				//				console.log(JSON.stringify(msg));
				$('#dinner_li').attr('style', 'height:auto;');
				$('.dinner').css({
					'height': '3.32rem',
					'line-height': '3.32rem'
				});
				for(var i = 0; i < msg.data.length; i++) {
					if(i == 0) {
						h('#dinner_info').html(msg.data[i]);
					} else {
						var src = msg.data[i];
						var html = '';
						html += '<div class="image-item">' +
							'<img src="' + src + '" data-preview-src="" data-preview-group="3" />' +
							'</div>';
						$("#dinner_show_info").append(html).show();
					}
				}
				$(".mui-backdrop,.mui-backdrop li").hide(200);
				plus.nativeUI.closeWaiting();
				mask.close();//关闭遮罩层
				$(".bg-nc").hide();
			}
		});
	}

	function closeIframe() {
		layerClose();
	} 

	function closeIframe2() {
		$(".mui-backdrop,.mui-backdrop li").hide();
	}
	var shareWx = null;
	var shareQQ = null;
	var shareWb = null;

	function shareNow(type) { //分享方法
		plus.share.getServices(function(services) { //成功方法
			for(var k in services) {
				if(services[k].id == 'weixin') {
					shareWx = services[k];
				} else if(services[k].id == 'qq') {
					shareQQ = services[k];
				} else if(services[k].id == 'sinaweibo') {
					shareWb = services[k];
				}
			}
			if(shareWx == null) {
				mui.toast('您没有安装微信');
				return;
			}
			if(shareQQ == null) {
				mui.toast('您没有安装QQ');
				return;
			}
			if(shareWb == null) {
				mui.toast('您没有安装微博');
				return;
			}
			var w = $("#share_content").width();
			var h = $("#share_content").height();

			//要将 canvas 的宽高设置成容器宽高的 2 倍
			var canvas = document.createElement("canvas");
			canvas.width = w * 3;
			canvas.height = h * 3;
			canvas.style.width = w + "px";
			canvas.style.height = h + "px";
			var context = canvas.getContext("2d");
			//然后将画布缩放，将图像放大两倍画到画布上 
			context.translate(0, -100);
			context.scale(3, 3);
			html2canvas(document.getElementById("share_content"), {
				allowTaint: true,
				taintTest: false,
				canvas: canvas,
				onrendered: function(canvas) {
					canvas.id = "mycanvas";
					var dataUrl = canvas.toDataURL();
					var bitmap = new plus.nativeObj.Bitmap();
					bitmap.loadBase64Data(dataUrl, function() {
						console.log("创建成功");
					}, function() {
						console.log("创建失败");
					});
					bitmap.save("_doc/share.jpg", {
						overwrite: true,
						quality: 100
					}, function(i) {
						console.log('保存图片成功：' + JSON.stringify(i));
						if(i.size != 0) {
							var msg = {
								content: '尼基营养',
								pictures: ['_doc/share.jpg']
							};
							if(type == 1) { //分享到好友
								msg.extra = {
									scene: "WXSceneSession"
								}
								if (shareWx.authenticated) {//已经认证
										shareWx.send(msg, function() { //分享成功方法
											mui.toast('分享成功');
										}, function(e) { //分享失败方法
											mui.toast('您取消了分享');
										});
								} else {//未认证
									shareWx.authorize(function(){
										shareWx.send(msg, function() { //分享成功方法
											mui.toast('分享成功');
										}, function(e) { //分享失败方法
											mui.toast('您取消了分享');
										});
									}, function(e){
										mui.toast("授权失败");
									})
								}
							} else if(type == 2) { //分享到朋友圈
								msg.extra = {
									scene: "WXSceneTimeline"
								}
								if (shareWx.authenticated) {//已经认证
										shareWx.send(msg, function() { //分享成功方法
											mui.toast('分享成功');
										}, function(e) { //分享失败方法
											mui.toast('您取消了分享');
										});
								} else {//未认证
									shareWx.authorize(function(){
										shareWx.send(msg, function() { //分享成功方法
											mui.toast('分享成功');
										}, function(e) { //分享失败方法
											mui.toast('您取消了分享');
										});
									}, function(e){
										mui.toast("授权失败");
									})
								}
							} else if(type == 3) {//分享到QQ好友
								if (shareQQ.authenticated) {//已经认证
										shareQQ.send(msg, function() { //分享成功方法
											mui.toast('分享成功');
										}, function(e) { //分享失败方法
											mui.toast('您取消了分享');
										});
								} else {//未认证
									shareQQ.authorize(function(){
										shareQQ.send(msg, function() { //分享成功方法
											mui.toast('分享成功');
										}, function(e) { //分享失败方法
											mui.toast('您取消了分享');
										});
									}, function(e){
										mui.toast("授权失败");
									})
								}
							} else { //分享到微博
								if (shareWb.authenticated) {//已经认证
										shareWb.send(msg, function() { //分享成功方法
											mui.toast('分享成功');
										}, function(e) { //分享失败方法
											mui.toast('您取消了分享');
										});
								} else {//未认证
									shareWb.authorize(function(){
										shareWb.send(msg, function() { //分享成功方法
											mui.toast('分享成功');
										}, function(e) { //分享失败方法
											mui.toast('您取消了分享');
										});
									}, function(e){
										mui.toast("授权失败");
									})
								}
							}
							bitmap.clear();
						}
					}, function(e) {
						console.log('保存图片失败：' + JSON.stringify(e));
						mui.toast('生成图片失败！');
						bitmap.clear();
					});
				}
			});
		}, function() { //失败方法
			mui.toast('获取分享服务失败');
		});
	}
	var id = $("#F_CKJLBS");
	mui.previewImage();