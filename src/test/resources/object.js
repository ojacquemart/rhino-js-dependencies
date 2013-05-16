function purl() {

    return {
        // return path segments
        segment: function (seg) {
        },

        // return fragment segments
        fsegment: function (seg) {
        }

    };

};

var f = purl();
f.segment();

// marginTop must not be present in the functions list.
$("#foo").animate({
    marginTop: "10px"
});