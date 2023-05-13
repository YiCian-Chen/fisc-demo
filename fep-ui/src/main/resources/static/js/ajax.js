/*
  ajax
 */
function formSubmit(formId) {
    let form = document.querySelector('#' + formId);
    let url = form.getAttribute('action');
    let method = form.getAttribute('method');
    let configs = {
        async: false
    };
    let data = {};
    for (let [key, value] of new FormData(form)) {
        data[key] = value;
    }
    switch (method) {
        case 'GET':
            get(url, data);
            break;
        case 'POST':
            post(url, data);
            break;
    }
}

function post(url, data, configs) {
    xhr(url, "POST", data, configs);
}

function get(url, data, configs) {
    xhr(url, "GET", data, configs);
}

function xhrConfig(configs){
    let _defaultXhrConfig = {
        async: true,
        mimeType: 'application/json',
        responseType: 'json',
        timeout: 0,
        ontimeout: function() {},
        before: onLoading,
        complete: completeLoading,
        onload: function() { },
        error: failedHandler,
        success: successDefaultHandler
    }
    if( isEmpty(configs) ) {
        configs = {};
    }
    $.each(_defaultXhrConfig, function (k, v){
       if( isBlank(configs[k]) ) {
           configs[k] = v;
       }
    });
    return configs;
}
function xhr(url, method, data, configs) {
    configs = xhrConfig(configs);
    document.querySelector('body').click();
    let reqBody = null;
    if( !isEmpty(data) ) {
        reqBody = JSON.stringify(data);
    }
    if( method == 'GET' ) {
        reqBody = null;
        url = url + '?' + $.param(data);
    }

    let xhr = new XMLHttpRequest();
    xhr.open(method, url.replace(/\|/g, '%7C'), configs.async);
    xhr.timeout = configs.timeout;
    xhr.ontimeout = configs.ontimeout;
    xhr.responseType = configs.responseType;
    xhr.overrideMimeType(configs.mimeType);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=utf-8');
    if(document.querySelector('meta[name=token]') != undefined) {
        xhr.setRequestHeader("Authorization", 'Bearer ' + document.querySelector('meta[name=token]').getAttribute('content'));
    }
    if( !isEmpty(configs.headers) ) {
        $.each(configs.headers, function (k, v) {
            xhr.setRequestHeader(k, document.querySelector(v).value);
        });
    }
    configs.before();
    xhr.send(reqBody);
    xhr.onload = configs.onload;
    xhr.onerror = configs.error;
    xhr.onreadystatechange = function () {
        if( xhr.readyState == 4 ) {
            if( xhr.status == 200 || xhr.status == 201 ) {
                configs.success(xhr.response, xhr.status, xhr);
            } else {
                configs.error(xhr);
            }
        }
    }
    xhr.onloadend = configs.complete;
}

function failedHandler(xhr){
    console.log(xhr);
    if (xhr.status === 400) {
        if(xhr.response && xhr.response['responseMessage']){
            let message = xhr.response['responseMessage'];
            if(message.indexOf(':') > 0 && message.indexOf("Invalid input.") !== -1){ // ESAPI validator
                let field = message.split(':')[0];
                if(field){
                    const $fieldName = $('[name=' + field + ']');
                    if($fieldName){
                        $fieldName.addClass('is-invalid');
                        $fieldName.removeClass('is-valid');
                    }
                    $('.alert').text(message);
                    $('.alert').show();
                } else {
                    Swal.fire ({
                        icon: "error",
                        title: '檢核錯誤',
                        text: message
                    });
                }
            }else{
                Swal.fire ({
                    icon: "error",
                    title: '檢核錯誤',
                    text: message
                });
            }
        } else if (xhr['responseMessage']) {
            Swal.fire ({
                icon: "error",
                title: '檢核錯誤',
                text: xhr['responseMessage']
            });
        }else{
            Swal.fire ({
                icon: "error",
                title: '檢核類錯誤',
                text: getResponseMessage(xhr)
            });
        }
    } else if(xhr.status === 500) {
        Swal.fire ({
            icon: "error",
            title: '系統錯誤',
            text: getResponseMessage(xhr)
        });
    } else if(xhr.status === 503) {
        Swal.fire ({
            icon: "error",
            title: '主機無回應',
            text: "["+xhr.responseJSON['responseCode']+"]"+xhr.responseJSON['responseMessage']
        });
    } else if(xhr.status === 404) {
        Swal.fire ({
            icon: "error",
            title: '資料不存在',
            text: xhr.responseJSON['responseMessage']
        });
    } else if(xhr.status === 409) {
        Swal.fire ({
            icon: "error",
            title: '新增資料重複',
        });
    } else if(xhr.status === 401) {
        Swal.fire ({
            icon: "error",
            title: '您重複登入，請重新登入',
            willClose: () => {
                Swal.hideLoading();
                document.location.href = $('#_loginUrl').val();
            }
        });
    } else if(xhr.status === 403) {
        Swal.fire ({
            icon: "error",
            title: '您沒此權限',
            willClose: () => {
                Swal.hideLoading();
                document.location.href = $('#_indexUrl').val();
            }
        });
    } else {
        Swal.fire ({
            icon: "error",
            title: 'ERROR:'+xhr.status,
            willClose: () => {
                Swal.hideLoading();
                document.location.href = $('#_indexUrl').val();
            }
        });
        return;
    }
}


function successHandler(data, status, xhr){
    $.each($('#dataTables tr'), function (idx, row){
        $(row).removeClass('table-danger');
        $(row).removeClass('table-success');
        $(row).tooltip('dispose');
    });
    let title = '送出成功';
    let html = '返回搜尋列表';
    let icon = 'success';
    let redirectPage = false;
    if($('#_redirectPageFlag')){ if( $('#_redirectPageFlag').val() == 'true' ) { redirectPage = true; } }
    if(data){
        if(data.responseMessage){
            html = data.responseMessage;
        }
    }
    //多筆格式的回覆
    if(data.results) {
        html = '此次執行共<i class="text-primary">'+data.total+'</i>筆';
        if(data.success == 0){
            icon = 'error';
            title = '送出失敗';
        }else{
            html += '<br>成功<i class="text-primary">'+data.success+'</i>筆';
        }

        if(data.success != 0 && data.failed != 0){
            icon = 'question';
            title = '送出部分成功';
        }
        if(data.failed != 0){
            html += '<br>失敗<i class="text-danger">'+data.failed+'</i>筆';
        }

        if(data.results.length == 1){
            //單筆
            if(icon == 'success'){
                refreshTable();
            }
            Swal.fire({
                title: title,
                html: data.results[0].recordMessage,
                icon: icon,
                willClose: function() {
                    $('#dtlModal').modal('hide');
                    if(redirectPage) {
                        onLoading();
                        backToQueryPage($('#queryForm'));
                    }
                }
            });
        }else{
            //多筆
            let keys = tableConfig.keys;
            if(keys) {
                //有設定Key才找回該筆
                $.each(data.results, function (idx, datas){
                    $.each(dataTable.rows({ filter : 'applied'}).data(), function (i, d){
                        let flag = true;
                        $.each(keys, function (ii, key){
                            if(datas[key] != d[key]){
                                flag = false;
                            }
                        });
                        if( flag ){
                            if(datas.recordStatus){
                                //抓原始row number
                                let index = dataTable.rows({ filter : 'applied'})[0][i];
                                if(datas.recordStatus == 'NOK'){
                                    $(dataTable.row(index).node()).addClass('table-danger');
                                    let message = datas.recordMessage;
                                    if(message == undefined || message == null || message == "") {
                                        message = "未知錯誤";
                                    }
                                    $(dataTable.row(index).node()).tooltip( {title: message} );
                                }else{
                                    $(dataTable.row(index).node()).addClass('table-success');
                                }
                            }
                        }
                    });
                });
            }
            if(icon == 'success'){
                refreshTable();
            }
            if( title == null ){
                title = '未知狀態';
            }
            Swal.fire({
                title: title,
                html: html,
                icon: icon,
                willClose: function() {
                    if(redirectPage) {
                        onLoading();
                        backToQueryPage($('#queryForm'));
                    }
                }
            });
        }
    } else {
        alertDialog({ title: title, html: html, icon: icon, formId : 'queryForm'});
    }

}