(function($){
	///copy by��׿���ǣ��������http://android.myapp.com/myapp/detail.htm?apkName=com.shandong.mm.androidstudy
     var defaults={
	     cjtable:"cjtable",
		 cjtd:"cjtd"
	 };
	 function Point(){
		   this.x=0;
		   this.y=0;
     }
	 
	  $.fn.selectTable=function(){	             
			 this.find("tbody tr td:gt(0)").each(function(){
					 var colIndex=$(this).index();
					   if(colIndex==0){
						return;
					 }					 
					 $(this).attr("isEdit",'no').addClass("selectClass").attr("startMouseDown","no");
			 });
	 }

	 $.fn.createEditTable=function(){
	     var tb=this;
		 this.addClass(defaults.cjtable);
		 this.find("td").addClass(defaults.cjtd);
		 $(document).bind("mousedown",docDownhandle);
		 //������갴���¼�
         this.find("tbody tr td:gt(0)").mousedown(function(event){	
              event.stopPropagation();		 
			 if(event.button!=2){
			        var colIndex=$(this).index();
					if(colIndex==0){
					  return;
					}
					if($(this).find("input").get().length>0){
						  return;
					}
					endEdit();
					
				    tb.find("td").removeClass("selectClass").attr("startMouseDown","no");					
					$(this).attr("startMouseDown","yes");
					$(this).addClass("selectClass");				
					tb.find("tbody td").each(function(){
					     var colIndex=$(this).index();
							if(colIndex==0){
							  return;
							}
					     $(this).bind("mousemove",cellMove);
					});
					$(document).bind("mouseup",uphandle);	
					
				}		
         });
//		   }).bind("dblclick",dblclick);	
        		   
		   $(document).bind("keydown",nt);
		   ///����ƶ��Ĵ����¼�
		    function cellMove(){			   
			    setSelectRegion.call(this);
	        }
			//����Ƴ�table��̨�����¼�
			function docDownhandle(){
			   // tb.find("td").removeClass("selectClass").attr("startMouseDown","no");
			    endEdit();
			}
			//���̧��Ĵ����¼�
			 function uphandle(tempTd){
			      tb.find("tbody td").unbind("mousemove",cellMove);
				 // $(tempTd).parent().parent().find("td").unbind("mousemove",cellMove);
				  $(this).unbind("mouseup",uphandle);
					 // alert("up");
			  }
			  ///����ѡ������
			  function setSelectRegion(){
					var spoint=new Point();
					var epoint=new Point();
					 var startCells=$(this).parent().parent().find("td[startMouseDown='yes']").get();
					 if(startCells.length==0){
						 return ;
					 }
					 
					 var startCell=startCells[0];
					 endCell=this;
					 if(startCell==endCell){
						return;
					 }
					 $(this).parent().parent().find("td").removeClass("selectClass");
					spoint.x=$(startCell).index()<$(endCell).index()?$(startCell).index():$(endCell).index();
					spoint.y=$(startCell).parent().index()<$(endCell).parent().index()?$(startCell).parent().index():$(endCell).parent().index();
					epoint.x=$(startCell).index()>$(endCell).index()?$(startCell).index():$(endCell).index();
					epoint.y=$(startCell).parent().index()>$(endCell).parent().index()?$(startCell).parent().index():$(endCell).parent().index();
					for(var i=spoint.y;i<=epoint.y;i++){
					   for(var j=spoint.x;j<=epoint.x;j++){
						  tb.find("tbody tr").eq(i).children().eq(j).addClass("selectClass");					  
					   }
					}
					
			 }
			 ///�ӵ�Ԫ���л�ȡֵ
			 function getValueFromCell(){
					return $(this).text();
			 }
			 ////处理双击编辑事件
//			 function dblclick(event){    
//				  startEdit.call(this);
//			  }
			  /// ///开始编辑
//			  function startEdit(startEditValue){
//					if($(this).find("input").get().length>0){
//					   return;
//					}
//					endEdit();
//				   var inpwidth=$(this).css("width");
//				   var inpheight=$(this).css("height");
//				   
//				   var cellValue=getValueFromCell.call(this);
//				   if(startEditValue!=null){
//				      cellValue=startEditValue;
//				   }
//				   $(this).attr("isEdit","yes");
//				  $(this).parent().parent().find("td").removeClass("selectClass");		
//				   ///����inputֻ�����������ֺ�С���
//				   var inp;		   
//				   inp=$("<input type='text' style='width:50px;height:30px'>"+"</input>");
//				   
//				   inp.on('keyup', function (event) {
//						var $inp = $(this);
//						//��Ӧ����¼����������ҷ�����ƶ� 
//						event = window.event || event;
//						if (event.keyCode == 37 | event.keyCode == 39) {
//							return;
//						}
//						//�Ȱѷ����ֵĶ��滻�����������ֺ�. 
//						$inp.val($inp.val().replace(/[^\d.]/g, "").
//							//ֻ����һ��С���              
//							replace(/^\./g, "").replace(/\.{2,}/g, ".").
//							//ֻ������С������λ
//							replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
//				});
//					inp.on('blur', function () {
//						var $inp = $(this);
//						$inp.val(($amountInput.val().replace(/\.$/g, "")));
//                   });
//				   
//				   inp.val(cellValue);
//				   inp.css("width",inpwidth);
//				   inp.css("height",inpheight);			
//				   $(this).html(inp);
//				   $("#t1").focus();
//				   inp.focus();												   
//			 }
			 ///����༭
			 function endEdit(){ 
				var currentEditCells=tb.find("tbody td[isEdit='yes']").get();
				var currentEditCell;
				if(currentEditCells.length>0){
					currentEditCell=currentEditCells[0];
					if($(currentEditCell).find("input").get().length==0){
						  return;
					}
					 var ivalue=$(currentEditCell).find("input").val();
					 $(currentEditCell).html("");
					 $(currentEditCell).append(ivalue);
					 $(currentEditCell).attr("isEdit",'no');
				}		
			  }
			  ///�Ȱ�Ҫճ�����ݷ���textArea�ؼ���
			  function dealwithData(event) { 
				var ss = document.getElementById("textArea"); 
				var reg=/[\s]+/g;					
				var arr=ss.value.split(reg);			         
				ss.blur(); 
				dealCtrlV(arr);
			  } 
			  //����ctrl+v
			 function dealCtrlV(arr){
				   var arrtd=tb.find(".selectClass").get();
				   for(var i=0;i<arrtd.length;i++){
					   if(i<arr.length&&arr[i]!=""){
						  if(!isNaN(arr[i])){
							  	 $(arrtd[i]).html("");
					             $(arrtd[i]).append(arr[i]);
					            $(arrtd[i]).attr("isEdit",'no');
                          //     $(arrtd[i]).html(arr[i]);
                         }
  
					   }
					   
				   }
					
			}	
			///����������������ƶ�
			function nt(event) { 
					if (event.ctrlKey && event.keyCode == 86) { 
						var ss = document.getElementById("textArea"); 
						$(ss).focus(); 
						$(ss).select(); 
						// ��50���룬keyPress�¼���������ȥ������� 
						setTimeout(dealwithData,50); 
					} else{
					    
					     var currentEditCells=tb.find("tbody td[isEdit='yes']").get();
						  var currentEditCell;
						 if(currentEditCells.length>0){
						    currentEditCell=currentEditCells[0];
						   
						 }
						
						 var arrtd=tb.find("tbody td.selectClass").get();						
						 if(arrtd.length==1||currentEditCell!=null){
								 var currentSelectCell;
								 if(currentEditCell!=null){
									currentSelectCell=currentEditCell;
								 }else{
									currentSelectCell=arrtd[0];
								 }
								  

								  var myColIndex=$(currentSelectCell).index();
								   var myRowIndex=$(currentSelectCell).parent().index();
								  
								if(event.keyCode == 37){//LEFT��
										event.stopPropagation();
										 event.preventDefault();
										endEdit.call(currentSelectCell);
										tb.find("td").removeClass('selectClass');
										if(myColIndex==1){
										   myColIndex=0;
										}
										$(currentSelectCell).parent().find('td:eq('+(--myColIndex)+')').addClass('selectClass');										                     
								}else if (event.keyCode == 39){//RIGHT��
										event.stopPropagation();
										 event.preventDefault();
										endEdit.call(currentSelectCell);
										tb.find("td").removeClass('selectClass');
										if($(currentSelectCell).parent().find('td:eq('+(++myColIndex)+')').get().length==0){
										   $(currentSelectCell).parent().find('td:eq(1)').addClass('selectClass');
										}else{
										   $(currentSelectCell).parent().find('td:eq('+(myColIndex)+')').addClass('selectClass');
										 }										
			   
								}else if (event.keyCode == 38){//UP��
									event.stopPropagation();
									 event.preventDefault();
									endEdit.call(currentSelectCell);
									tb.find("td").removeClass('selectClass');
								   $(currentSelectCell).parent().parent().find('tr:eq('+(myRowIndex-1)+')').find('td:eq('+(myColIndex)+')').addClass('selectClass');				
								}else if (event.keyCode == 40){//DOWN��
									event.stopPropagation();
									event.preventDefault();
									endEdit.call(currentSelectCell);
									tb.find("td").removeClass('selectClass');
									if($(currentSelectCell).parent().parent().find('tr:eq('+(myRowIndex+1)+')').get().length==0){
									   $(currentSelectCell).parent().parent().find('tr:eq(0)').find('td:eq('+(myColIndex)+')').addClass('selectClass');
									}else{
										$(currentSelectCell).parent().parent().find('tr:eq('+(myRowIndex+1)+')').find('td:eq('+(myColIndex)+')').addClass('selectClass');
									 }				
								}else {
									 var re=/[0-9,a-z,A-Z]$/; 
									 var str=String.fromCharCode(event.keyCode); 
									  if(re.test(str)){
										 if(currentEditCell==null&&currentSelectCell!=null){
										   startEdit.call(currentSelectCell,"");
											  //  alert(re.test(str));
										 }
								   }
								}
						 }
					}					
				 }
	 }
})(jQuery);