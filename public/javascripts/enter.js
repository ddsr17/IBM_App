/**
 * Created by deepanshu on 16/9/15.
 */

var URL = "http://localhost:9000/"

$(document).ready(function() {
    $.ajax({
        method: "GET",
        url: URL + "getarticles"
    })
        .done(function (data) {

            $("#accordion").slimScroll({
                height: '750px',
                width: '320px'
            });
            var temp = 1;
            $.each(data,function(i,obj)
            {

                console.log(obj)
                $("#id"+temp).append(obj.title)
                $("#di"+temp).append(obj.date)
                $("#di"+temp).append("<br>" + obj.name)
                $("#di"+temp).append("<br>" + obj.url)
                $("#di"+temp).append("<br>" + obj.category)
                ++temp;
            })
            //var next = JSON.parse(data)
            //console.log(data.title)
           // console.log(next)
            //console.log(next.title)
        })
})