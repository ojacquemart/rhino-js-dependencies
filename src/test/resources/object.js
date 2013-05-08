function purl( url, strictMode ) {

    return {

        // get various attributes from the URI
        attr : function( attr ) {
            attr = aliases[attr] || attr;
            return typeof attr !== 'undefined' ? this.data.attr[attr] : this.data.attr;
        },

        // return query string parameters
        param : function( param ) {
            return typeof param !== 'undefined' ? this.data.param.query[param] : this.data.param.query;
        },

        // return fragment parameters
        fparam : function( param ) {
            return typeof param !== 'undefined' ? this.data.param.fragment[param] : this.data.param.fragment;
        },

        // return path segments
        segment : function( seg ) {
            if ( typeof seg === 'undefined' ) {
                return this.data.seg.path;
            } else {
                seg = seg < 0 ? this.data.seg.path.length + seg : seg - 1; // negative segments count from the end
                return this.data.seg.path[seg];
            }
        },

        // return fragment segments
        fsegment : function( seg ) {
            if ( typeof seg === 'undefined' ) {
                return this.data.seg.fragment;
            } else {
                seg = seg < 0 ? this.data.seg.fragment.length + seg : seg - 1; // negative segments count from the end
                return this.data.seg.fragment[seg];
            }
        }

    };

};

var f = purl();
f.attr();