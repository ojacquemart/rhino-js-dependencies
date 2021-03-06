<!DOCTYPE html>
<!-- saved from http://bootswatch.com/united/ -->
<html ng-app>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Dr Rhino</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Dr Rhino - Dependencies report">
    <meta name="author" content="Olivier Jacquemart">
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="http://bootswatch.com/united/bootstrap.min.css" rel="stylesheet">
    <link href="http://bootswatch.com/default/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="http://bootswatch.com/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://bootswatch.com/css/bootswatch.css" rel="stylesheet">
    <style type="text/css"></style>
    <style type="text/css">
        a {
            cursor: pointer;
        }

        .file-outline {
            min-height: 250px;
            max-height: 250px;
            overflow: auto
        }

        section {
            margin-top: 0;
            padding-top: 0;
        }

            /**
             *  Column center
             */
        .well {
            border-top-left-radius: 0;
            border-top-right-radius: 0;
            border-bottom-left-radius: 4px;
            border-bottom-right-radius: 4px;
        }

        .paths-files-header-column {
            font-size: 13px;
            text-align: center;
            background: #DDD;
            border-top-right-radius: 8px;
            border-top-left-radius: 8px;
        }

            /**
             * Search bar query.
             */
        .navbar-search .search-query {
            padding-left: 29px;
            background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAJ5JREFUeNpi+P//PwMQMANxERCfAeI/UBrEZwbJQ9WAFR0A4u1AbAnEbFB6O1ScGaawGoi3wHQiYyBYDZKHKbwHxLo4FOqC5GEKf4Ksw6EQ5IyfIDYTkPEUiNUZsAOQ+F9GRkYJEKcFiDficSOIcRjE4QTiY0C8DuRbqAJLKP8/FP9kQArHUiA+jySJjA8w4LAS5KZd0MAHhaccQIABALsMiBZy4YLtAAAAAElFTkSuQmCC);
            background-repeat: no-repeat;
            background-position: 12px 8px;
        }

        .navbar-search .search-query:focus, .navbar-search .search-query.focused {
            /*padding-left: 50px;*/
            background-position: 13px 9px;
        }

    </style>
</head>
<body ng-controller="DrRhinoController" class="preview" id="top" data-spy="scroll" data-target=".subnav"
      data-offset="80">
<!-- Navbar
  ================================================== -->
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            </a>
            <a class="brand" href="#">Dr Rhino</a>
            <a href="https://github.com/ojacquemart/rhino-js-dependencies" target="_blank">
                <img style="position: absolute; top: 0; right: 0; border: 0;"
                     src="https://s3.amazonaws.com/github/ribbons/forkme_right_white_ffffff.png"
                     alt="Fork me on GitHub">
            </a>

            <form class="navbar-search">
                <input class="search-query span2" placeholder="Search" ng-model="search.$" type="text">
            </form>
        </div>
    </div>
</div>

<div class="container">

    <!-- Masthead
    ================================================== -->
    <header class="jumbotron subhead" id="overview">
        <div class="row">
            <div class="span6">
                <h1>{{report.projectName}}</h1>

                <p class="lead">Dependencies report.</p>
            </div>

            <div class="span6">
                <div class="well" style="padding: 25px 25px 15px 25px;">
                    <ul class="nav">
                        <li class="clearfix"><span class="span2">Root directory:</span>{{report.rootJsDir}}</li>
                        <li class="clearfix"><span class="span2">Date:</span>{{report.date}}</li>
                        <li class="clearfix"><span class="span2">Files:</span>{{report.numberOfFiles}}</li>
                        <li class="clearfix"><span class="span2">Minified files:</span>{{report.numberOfMinifiedFiles}}
                        </li>
                        <li class="clearfix"><span class="span2">Lines of code (LOC):</span>{{report.numberOfLoc}}</li>
                    </ul>
                    <div style="clear:both"></div>
                </div>
            </div>
        </div>
        <div class="subnav">
            <ul class="nav nav-pills">
                <li><a href="#files">Files</a></li>
                <li><a href="#graphic">Graphic</a></li>
            </ul>
        </div>
    </header>

    <!-- Typography
    ================================================== -->
    <section id="files">
        <div class="paths" ng-repeat="path in report.paths">

            <ul class="breadcrumb">
                <li>{{path.name}} ({{path.numberOfFiles}} files | {{path.numberOfLoc}} loc)</li>
            </ul>
            <div id="{{file.name}}" class="paths-files" ng-repeat="file in path.files | filter:search">
                <div class="page-header">
                    <h4>{{file.name}}</h4>
                </div>

                <!-- Headings & Paragraph Copy -->
                <div class="row">
                    <div class="span4">
                        <div class="paths-files-header-column">
                            <span>Functions</span>
                        </div>
                        <div class="well">
                            <ul class="file-outline">
                                <li ng-repeat="function in file.functions| filter:search">{{function.name}}</li>
                            </ul>
                        </div>
                    </div>
                    <div class="span4">
                        <div class="paths-files-header-column">
                            <span>Functions called</span>
                        </div>
                        <div class="well">
                            <ul class="file-outline">
                                <li ng-repeat="function in file.functionCalls| filter:search">{{function.name}}</li>
                            </ul>
                        </div>
                    </div>
                    <div class="span4">
                        <div class="paths-files-header-column">
                            <span>Files references</span>
                        </div>
                        <div class="well">
                            <ul class="file-outline">
                                <li ng-repeat="usage in file.usages| filter:search">
                                    <a ng-click="scrollTo(usage.name)">{{usage.name}}</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section id="graphic">
        <div class="page-header">
            <h4>TODO</h4>
        </div>
    </section>
    <!-- Footer
    ================================================== -->
    <hr>

    <footer id="footer">
        <p class="pull-right"><a href="#top">Back to top</a></p>
        By <a href="https://twitter.com/ojacquemart">Olivier Jacquemart</a>.
    </footer>

</div>
<!-- /container -->

<!-- javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://bootswatch.com/js/jquery.smooth-scroll.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.1.5/angular.min.js"></script>
<script type="text/javascript">
    function DrRhinoController($scope) {

        // Dependencies report.
        $scope.report = ${report.toJson()};

        $scope.scrollTo = function (id) {
            var element = $(document.getElementById(id));
            $('html, body').animate({
                scrollTop: element.offset().top - 100
            }, 1000);
        }

        // onLoad...
        initBotswatch();
        function initBotswatch() {
            $('a[rel=tooltip]').tooltip({ 'placement': 'bottom' });
            $('.navbar a, .subnav a').smoothScroll();

            // fix sub nav on scroll
            var $win = $(window),
                    $body = $('body'),
                    $nav = $('.subnav'),
                    navHeight = $('.navbar').first().height(),
                    subnavHeight = $('.subnav').first().height(),
                    subnavTop = $('.subnav').length && $('.subnav').offset().top - navHeight,
                    marginTop = parseInt($body.css('margin-top'), 10);
            isFixed = 0;


            $win.on('scroll', processScroll);

            function processScroll() {
                var i, scrollTop = $win.scrollTop();

                if (scrollTop >= subnavTop && !isFixed) {
                    isFixed = 1;
                    $nav.addClass('subnav-fixed');
                    $body.css('margin-top', marginTop + subnavHeight + 'px');
                } else if (scrollTop <= subnavTop && isFixed) {
                    isFixed = 0;
                    $nav.removeClass('subnav-fixed');
                    $body.css('margin-top', marginTop + 'px');
                }
            }

            processScroll();
        }
    }
</script>
</body>
</html>