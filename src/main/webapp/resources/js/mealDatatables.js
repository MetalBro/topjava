var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        // "pagingType" : "full_numbers",
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (data) {
                    return data.toString().replace("T", " ");
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            // {
            //     "defaultContent": "Edit",
            //     "orderable": false
            // },
            // {
            //     "defaultContent": "Delete",
            //     "orderable": false
            // },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).addClass(data.exceed ? 'exceeded' : 'normal')
        }
    });
    makeEditable();
});