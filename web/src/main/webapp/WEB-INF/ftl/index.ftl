<!DOCTYPE html>
<html>
<head>
    <title></title>
</head>
<script type="text/javascript" src="/resources/jquery/js/jquery-1.9.1.js"></script>
<body>

${hello};

<p><a id="bt" href="javascript:;">click me</a> </p>
<p><a id="bt2" href="javascript:;">click me</a> </p>

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

        $("#bt2").click(function(){
            $.ajax({
                type:"get",
                url:"/jared/json/test",
                data : {json :'{"message":"Message123","time":"time123","name":"test123"}'},
                success :function(r){
                    console.log(r);
                }
            } );
        });
    } )

</script>
</html>