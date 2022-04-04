console.log('hello');
var main = {
    init : function () {
        var _this = this;
        $('#btn-test-save').on('click', function () {
            _this.save_test();
        });
        $('#btn-save').on('click', function () {
            let fileLen= $("#d_file").children('input').length;
            if (fileLen==0){
                alert('상품 이미지를 등록해주세요.');
                return false;
            }
            let fileValidCount =0;
            $("#d_file").children('input').each(function(index,item){
                var fileValue = $(item).val().split("\\");
                var fileName = fileValue[fileValue.length-1]; // 파일명
                console.log(fileName);
                if (!fileName){
                    alert('상품 이미지를 등록해주세요.');
                    return false;
                } else {
                    fileValidCount++;
                }
                if (index== fileLen-1 // 모든 파일input에 대해 다 조사했는지 체크..
                && fileValidCount== fileLen){ // 파일input갯수대로 모두 파일이 등록됐는지 체크..
                    _this.save();
                }
            });


        });

        $('#btn-update').on('click', function () {
                let fileLen= $("#d_file").children('input').length;
                if (fileLen){
                    let fileValidCount =0;
                    $("#d_file").children('img').each(function(index,item){
                        var src = $(item).attr('src');
                        console.log(src);
                        if (!src){
                            alert('상품 이미지를 등록해주세요.');
                            return false;
                        } else {
                            fileValidCount++;
                        }
                        if (index== fileLen-1
                            && fileValidCount== fileLen){ // 파일input갯수대로 모두 파일이 등록됐는지 체크..
                            _this.update();
                        }
                    });
                } else{
                    _this.update();
                }

        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save_test : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/test/hello';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    save : function () {
        console.log('save');
        var formData = new FormData($('#fileForm')[0]);

        $.ajax({
            type: 'POST',
//            contentType : 'multipart/form-data', // 필수
            url: '/api/v1/goods',
            data: formData, // 필수
            processData: false, // 필수
            contentType: false, // 필수
            cache: false,
        }).done(function() {
            alert('상품이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var formData = new FormData($('#fileForm')[0]);

        var id = $('#id').val();

        $.ajax({
            type: 'POST',
//            enctype : 'multipart/form-data', // 필수
            url: '/api/v1/goods/'+id,
            data: formData, // 필수
            processData: false, // 필수
            contentType: false, // 필수
            cache: false,
        }).done(function() {
            alert('상품이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        console.log('delete')
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/goods/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('상품이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();