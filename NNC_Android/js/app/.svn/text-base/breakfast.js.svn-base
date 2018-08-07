mui.init()
var size = "3";
var files_bk = [];
var files_lun = [];
var files_din = [];
var index = 1;
var photoSzie = 0;

function appendFile(p,obj) {
	var $id = $(obj).attr("id");
	if($id == 'F_CKJLB'){
		files_bk.push({
			path: p,
			name: "uploadkey_" + index
		});
		index++;
	}else if($id == 'F_CKJL'){
		files_lun.push({
			path: p,
			name: "uploadkey_" + index
		});
		index++;
	}else if($id == 'F_CKJD'){
		files_din.push({
			path: p,
			name: "uploadkey_" + index
		});
		index++;
	}
}


//加载页面初始化需要加载的图片信息
//或者相册IMG_20160704_112620.jpg  
//imgId:图片名称：1467602809090或者IMG_20160704_112620  
//imgkey:字段例如：F_ZDDZZ  
//ID：站点编号ID,例如429  
//src：src="file:///storage/emulated/0/Android/data/io.dcloud.HBuilder/.HBuilder/apps/HBuilder/doc/upload/F_ZDDZZ-1467602809090.jpg"  
function showImgDetail(src,obj) {
	var $id = $(obj).attr("id");
	if($id == 'F_CKJLB'){
		var html = '';
		html += '<div class="image-item image-item-add">'+
				'<img id="" src="' + src + '" data-preview-src="" data-preview-group="5" />'+
				'</div>';
		$("#F_CKJLBS>.image-item-base").before(html);
		photoSzie++;
		if(photoSzie > 2) {
			$("#F_CKJLB").hide();
		}
	}else if($id == 'F_CKJL'){
		var html = '';
		html += '<div class="image-item image-item-add">'+
				'<img id="" src="' + src + '" data-preview-src="" data-preview-group="6" />'+
				'</div>';
		$("#F_CKJLUN>.image-item-base").before(html);
		photoSzie++;
		if(photoSzie > 2) {
			$("#F_CKJL").hide();
		}
	}else if($id =='F_CKJD'){
		
		var html = '';
		html += '<div class="image-item image-item-add">'+
				'<img id="" src="' + src + '" data-preview-src="" data-preview-group="7" />'+
				'</div>';
		$("#F_CKJDIN>.image-item-base").before(html);
		photoSzie++;
		if(photoSzie > 2) {
			$("#F_CKJD").hide();
		}

	}
	
}

//选取图片的来源，拍照和相册  
function showActionSheet(obj) {
	var actionbuttons = [{
		title: "拍照"
	}, {
		title: "相册选取"
	}];
	var actionstyle = {
		title: "选择照片",
		cancel: "取消",
		buttons: actionbuttons
	};
	plus.nativeUI.actionSheet(actionstyle, function(e) {
		if(e.index == 1) {
			getImage(obj);
		} else if(e.index == 2) {
			galleryImg(obj);
		}
	});
}

function galleryImg(obj) {
	plus.gallery.pick(function(e) {
		for(var i in e.files) {
			plus.io.resolveLocalFileSystemURL(e.files[i], function(entry) {
				compressImage(entry.toLocalURL(), entry.name,obj);
			}, function(e) {
				plus.nativeUI.toast("读取拍照文件错误：" + e.message);
			});
		}
	}, function(e) {
		console.log("取消选择图片");
	}, {
		filter: "image",
		multiple: true,
		maximum: 3 - photoSzie,
		system: false,
		onmaxed: function() {
			plus.nativeUI.alert('最多只能选择3张图片');
		}
	});

}
// 拍照  
function getImage(obj) {
	var cmr = plus.camera.getCamera();
	cmr.captureImage(function(p) {
		plus.io.resolveLocalFileSystemURL(p, function(entry) {
			compressImage(entry.toLocalURL(), entry.name, obj);
		}, function(e) {
			plus.nativeUI.toast("读取拍照文件错误：" + e.message);
		});
	}, function(e) {}, {
		filename: "_doc/camera/",
		index: 1
	});
}
//压缩图片  
function compressImage(url, filename, obj) {
	var path = "_doc/upload/" + filename; //_doc/upload/F_ZDDZZ-1467602809090.jpg
	appendFile(path,obj);
	plus.zip.compressImage({
			src: url, //src: (String 类型 )压缩转换原始图片的路径  
			dst: path, //压缩转换目标图片的路径  
			quality: 20, //quality: (Number 类型 )压缩图片的质量.取值范围为1-100  
			overwrite: true //overwrite: (Boolean 类型 )覆盖生成新文件  
		},
		function(event) {
			saveimage(event.target,obj);
		},
		function(error) {
			plus.nativeUI.toast("压缩图片失败，请稍候再试");
		});
}

//保存信息到本地  
/**   
 *   
 * @param {Object} url  图片的地址  
 * @param {Object} divid  字段的名称  
 * @param {Object} name   图片的名称  
 */
function saveimage(url,obj) {
	var wt = plus.nativeUI.showWaiting();
	var src = 'src="' + url + '"';
	showImgDetail(url,obj);
	wt.close();
}


function breakfastSubmit() {
	var task = plus.uploader.createUpload(
		URL + 'app_rest_ful/user_info_data_breakfast_save', {
			method: "POST"
		},
		function(t, status) { //上传完成   
			if(status == 200) {
				isLogin();
			} else {
				console.log("上传失败：" + status);
			}
		});
	for(var i = 0; i < files_bk.length; i++) {
		var f = files_bk[i];
		console.debug(JSON.stringify(files_bk[i]));
		task.addFile(f.path, {
			key: f.name
		});
	}
	files_bk = [];
	photoSzie = 0;
	var uid = localStorage.getItem('NNC_userInfoId');
	var subDate = localStorage.getItem('Submit_date');
	var breakfast = h("#breakfast_val").val();
	task.addData('breakfast', breakfast);
	task.addData('userInfoId', uid);
	task.addData('date', subDate);
	plus.storage.removeItem('Submit_date');
	task.start();
	
}

function lunchSubmit() {
	var task = plus.uploader.createUpload(
		URL + 'app_rest_ful/user_info_data_lunch_save', {
			method: "POST"
		},
		function(t, status) { //上传完成  
			if(status == 200) {
				isLogin();
			} else {
				console.log("上传失败：" + status);
			}
		});
	for(var i = 0; i < files_lun.length; i++) {
		
		var f = files_lun[i];
		task.addFile(f.path, {
			key: f.name
		});
	}
	files_lun = [];
	photoSzie = 0;
	var uid = localStorage.getItem('NNC_userInfoId');
	var subDate = localStorage.getItem('Submit_date');
	var lunch = h("#lunch_val").val();
	task.addData('lunch', lunch);
	task.addData('userInfoId', uid);
	task.addData('date', subDate);
	console.log(subDate);
	localStorage.removeItem('Submit_date');
	task.start();
}

function dinnerSubmit() {
	var task = plus.uploader.createUpload(
		URL + 'app_rest_ful/user_info_data_dinner_save', {
			method: "POST",
			async: false
		},
		
		function(t, status) { //上传完成  
			if(status == 200) {
				isLogin();
			} else {
				console.log("上传失败：" + status);
			}
		});
//	console.debug('晚饭'+files_din.length);
	for(var i = 0; i < files_din.length; i++) {
		
		var f = files_din[i];
		task.addFile(f.path, {
			key: f.name
		});
	}
	files_din = [];
	photoSzie = 0;
	var uid = localStorage.getItem('NNC_userInfoId');
	var subDate = localStorage.getItem('Submit_date');
	var dinner = h("#dinner_val").val();
	task.addData('dinner', dinner);
	task.addData('userInfoId', uid);
	task.addData('date', subDate);
	localStorage.removeItem('Submit_date');
	task.start();
}
