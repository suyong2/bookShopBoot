<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" isELIgnored="false"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
request.setCharacterEncoding("UTF-8");
%>
<h1>
    S3 이미지 업로더
</h1>
<div class="col-md-12">
    <div class="col-md-2">
        <form>
            <div class="form-group">
                <label for="img">파일 업로드</label>
                <input type="file" id="img">
            </div>
            <button type="button" class="btn btn-primary" id="btn-upload">업로드</button>
        </form>
    </div>
    <div class="col-md-10">
        <p><strong>결과 이미지입니다.</strong></p>
        <img src="" id="result-image">
    </div>
</div>


<script>
    $('#btn-upload').on('click', uploadImage);

    function uploadImage() {
        var file = $('#img')[0].files[0];
        var formData = new FormData();
        formData.append('data', file);

        $.ajax({
            type: 'POST',
            url: '/test/upload',
            enctype: 'multipart/form-data', // 필수
            data: formData,
            processData: false,
            contentType: false,
            cache: false
        }).done(function (data) {
            let arr=data.split('/');
            console.log(arr[arr.length-1]);
            $('#result-image').attr("src", "/down?imageFileName="+arr[arr.length-1]);
        }).fail(function (error) {
            alert(error);
        })
    }
</script>
