$(document).ready(function(){

    $('#contrib-plan').click(function(){
        location.href = "../plans"
    });

    $('#add-expenses').click(function(){
        var budget = $("#budget-value").text()
        $("#modal-budget").text(budget)
        $('.ui.mini.modal.reducebudget').modal('show');
    });

    $('#set-budget').click(function(){
        $('.ui.mini.modal.setbudget').modal('show');
    })

    $('.numericinput').on("keypress", function(event){
        $(this).val($(this).val().replace(/[^\d].+/, ""));
            if ((event.which < 48 || event.which > 57)) {
                event.preventDefault();
            }
    });

    $('.ui.mini.modal.reducebudget').modal({
        onApprove: function (e){
            if(e.hasClass("positive")){
                var value = parseInt($('#reduce-budget-input').val())
                var budget = parseInt($('#modal-budget').text())
                if(value > budget){
                    $('#reduce-budget-input').val("Wartość musi być niższa niż "+budget);
                    e.preventDefault()
                }
                else {
                    takeFromBudget(value)
                }
            }
        }
    })

    $('.ui.mini.modal.setbudget').modal({
            onApprove: function (e){
                if(e.hasClass("positive")){
                    var budget = parseInt($('#set-budget-input').val())
                    setNewBudget(budget)
                }
            }
        })

    function takeFromBudget(value){
        $.ajax({
            url         :       "budget/take",
            method      :       "put",
            data        :       {
                expenses: value
            },
            success     :       function(response){
                $("#budget-value").text(response.budget)
                $("#"+response.inmate+'_expenses').text(response.expenses)
            },
            error       :       function(){
                alert("Nie udało się pobrać pieniędzy z budżetu")
            }
        })
    }

    function setNewBudget(newBudget){
            $.ajax({
                url         :       "budget/set",
                method      :       "put",
                data        :       {
                    budget: newBudget
                },
                success     :       function(response){
                    $("#budget-value").text(newBudget)
                },
                error       :       function(){
                    alert("Nie udało się ustawić budżetu")
                }
            })
        }
})