$(document).ready(function(){

    var row = null;

    $(".ui.selectable.celled.table > tbody").delegate('tr', 'click', function(event) {
            var cost = this.children[2].innerHTML.replace("zł","")
            var planName = this.children[1].innerHTML
            row = this
            $('#plan-name').text(planName)
            $('#plan-cost').text(cost)
            $('.ui.mini.modal.updateplan').modal('show');
        });


    $('.numericinput').on("keypress", function(event){
        $(this).val($(this).val().replace(/[^\d].+/, ""));
            if ((event.which < 48 || event.which > 57)) {
                event.preventDefault();
            }
    });

    $('#addplan_button').click(function(){
        $('.ui.mini.modal.addplan').modal('show');
    })

    $('.ui.mini.modal.updateplan').modal({
        onApprove: function (e){
            if(e.hasClass("positive")){
                var planName = $('#plan-name').text()
                var contribution = parseInt($('.numericinput').val())
                var planCost = parseInt($('.plan-cost #value').text())
                if(contribution > planCost){
                    $('.numericinput').val("Wartość musi być niższa niż "+planCost);
                        e.preventDefault()
                    }
                    else {
                        sendRequest(contribution, planName)
                    }
                }
            }
        })

    $('.ui.mini.modal.addplan').modal({
        onApprove: function (e){
            if(e.hasClass("positive")){
                $("#addplan_form").submit();
            }
        }
    })

    function sendRequest(money, planName){
        var request = {
               "name" : planName,
               "contribution": money
              };
        $.ajax({
            url         :       "plans",
            method      :       "put",
            contentType :       "application/json",
            data        :       JSON.stringify(request),
            success     :       function(response){
                if(response > 0){
                    row.children[2].innerHTML = response+"zł"
                } else {
                    location.reload()
                }
            },
            error       :       function(){
                alert("Nie udało się wspomóc planu")
            }
        })
    }

    $('#addplan_form')
            .form({
                fields: {
                    name: {
                        rules: [
                            {
                                type: 'empty',
                                prompt: 'Podaj nazwę planu'
                            }
                        ]
                    },
                    cost: {
                        rules: [
                            {
                                type: "integer[1..]",
                                prompt: 'Koszt musi być wyższy od 0'
                            }
                        ]
                    }
                }
            });

})