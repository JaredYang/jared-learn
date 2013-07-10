<!DOCTYPE html>
<html>
<head>
    <title></title>
</head>
<script type="text/javascript" src="/resources/jquery/js/jquery-1.9.1.js"></script>
<body>

${hello};

<p><a id="bt" href="javascript:;">click me</a> </p>

</body>
<script type="text/javascript">
    $(function(){
        $("#bt").click(function(){
            $.ajax({
                type:"get",
                url:"/jared/delete/1",
                dataType : 'json',
                success :function(r){
                    console.log(r);
                }
            } );
        });
    } )

</script>
</html>