/**
 * Created by plaskorski on 3/26/17.
 */
$(document).ready(function() {
    // get current URL path and assign 'active' class
    var pathname = window.location.pathname;
    $('li > a[href="'+pathname+'"]').parent().addClass('active');
    $("#showMore").click(function () {
        $('.collapseRow').each(function() {
            $('.collapse.show').collapse('hide');
            $('.collapse').collapse('show');
        });
        if ($(this).text()=='Show All') {
            $(this).text('Hide');
        } else {
            $(this).text('Show All');
        }

    });
})