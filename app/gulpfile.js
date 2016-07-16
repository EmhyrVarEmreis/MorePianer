// Generated on 2015-04-06 using generator-jhipster 2.2.0
/* jshint camelcase: false */
'use strict';

var gulp = require('gulp'),
    gutil = require('gulp-util'),
    debug = require('gulp-debug'),
    inject = require('gulp-inject'),
    gulpif = require('gulp-if'),
    angularFilesort = require('gulp-angular-filesort'),
    angularTemplatecache = require('gulp-angular-templatecache'),
    runSequence = require('run-sequence'),
    browserSync = require('browser-sync'),
    minifyCss = require('gulp-minify-css'),
    useref = require('gulp-useref'),
    uglify = require('gulp-uglify'),
    ngAnnotate = require('gulp-ng-annotate'),
    clean = require('gulp-clean'),
    replace = require('gulp-replace'),
    wiredep = require('wiredep').stream;

var config = {
    app:              'src/main/webapp/',
    bootstrapDir:     'bower_components/bootstrap',
    materialFontsDir: 'bower_components/material-design-icons',

    work: 'target/work/',
    dist: 'target/classes/',

    port:           9000,
    liveReloadPort: 39561
};

gulp.task('clean', function() {
    return gulp.src(
        [config.dist + 'fonts',
            config.dist + 'scripts',
            config.dist + 'styles',
            config.dist + 'img',
            config.dist + 'index.html',
            config.work],
        {read: false})
        .pipe(clean());
});

gulp.task('copy', function() {
    return gulp.src(config.app + '/i18n/**')
        .pipe(gulp.dest(config.dist + '/i18n/'));
});

gulp.task('copy-fonts', function() {
    return gulp.src(config.bootstrapDir + '/fonts/**/*')
        .pipe(gulp.dest(config.dist + '/fonts'));
});
//
// gulp.task('copy-material-fonts', function() {
//     return gulp.src([
//         config.materialFontsDir + '/iconfont/*.eot',
//         config.materialFontsDir + '/iconfont/*.ttf',
//         config.materialFontsDir + '/iconfont/*.woff',
//         config.materialFontsDir + '/iconfont/*.woff2'
//     ]).pipe(gulp.dest(config.dist + '/fonts'));
// });

gulp.task('copy-images', function() {
    return gulp.src(config.app + '/img/**/*')
        .pipe(gulp.dest(config.dist + '/img'));
});

gulp.task('dev-enable', function() {
    return gulp.src(config.app + '/scripts/backend/const.js')
        .pipe(replace('CONST_DEV = false', 'CONST_DEV = true'))
        .pipe(gulp.dest(config.app + '/scripts/backend'));
});

gulp.task('dev-disable', function() {
    return gulp.src(config.app + '/scripts/backend/const.js')
        .pipe(replace('CONST_DEV = true', 'CONST_DEV = false'))
        .pipe(gulp.dest(config.app + '/scripts/backend'));
});

gulp.task('preprocess', function() {
    var injectStyles = gulp.src([
        config.app + '/**/*.css'
    ], {read: false});

    var injectScripts = gulp.src([
        config.app + '/scripts/**/*.js'
    ]).pipe(angularFilesort());

    var injectOptions = {
        relative: true
    };

    return gulp.src([config.app + '**/*.html', '!' + config.app + 'scripts/**/*.html'])
        .pipe(inject(injectScripts, injectOptions))
        .pipe(inject(injectStyles, injectOptions))
        .pipe(wiredep({
            onError: function(err) {
                if (err.toString().indexOf('bootstrap-sass is not installed') > 0) {
                    gutil.log('Ignoring error with \'bootstrap-sass is not installed\' in wiredep');
                } else {
                    gutil.log(err);
                }
            }
        }))
        .pipe(gulp.dest(config.work))
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('styles', function() {
    return gulp.src([config.app + '**/*.css'])
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('templates', function() {
    return gulp.src([config.app + 'scripts/**/*.html'])
        .pipe(angularTemplatecache({
            module: 'morepianer',
            root:   'scripts'
        }))
        .pipe(gulp.dest(config.work));
});

gulp.task('html', ['copy', 'copy-fonts', 'copy-images', 'templates', 'preprocess'], function() {

    var templatesInjectFile = gulp.src(config.work + 'templates.js', {read: true});
    var templatesInjectOptions = {
        starttag: '<!-- inject:templates -->',
        relative: true
    };

    return gulp.src([config.work + '**/*.html'])
        .pipe(inject(templatesInjectFile, templatesInjectOptions))
        .pipe(gulpif('*.js', ngAnnotate()))
        .pipe(gulpif('*.js', uglify()))
        .pipe(gulpif('*.css', minifyCss({compatibility: 'ie7'})))
        .pipe(useref({
            searchPath: [config.work, config.app]
        }))
        .pipe(gulp.dest(config.dist));
});

gulp.task('build', ['dev-disable', 'html'], function() {
});

gulp.task('serve', ['dev-enable', 'preprocess'], function() {

    gulp.watch([config.app + '**/*.html', config.app + '**/*.js', 'bower.json'], ['preprocess']);
    gulp.watch([config.app + '**/*.css'], function(event) {
        if (event.type === 'changed') {
            gulp.start('styles');
        } else {
            gulp.start('preprocess');
        }
    });

    browserSync.init({
        startPath: '/',
        server:    {
            baseDir: [config.work, config.app],
            routes:  {
                '/bower_components': 'bower_components'
            }
        },
        browser:   []
    })
});

gulp.task('default', function() {
    return runSequence(
        'clean',
        'build');
});
