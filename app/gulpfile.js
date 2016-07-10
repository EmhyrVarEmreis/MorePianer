// Generated on 2015-04-06 using generator-jhipster 2.2.0
/* jshint camelcase: false */
'use strict';

var gulp = require('gulp'),
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
    rev = require('gulp-rev'),
    revReplace = require('gulp-rev-replace'),
    clean = require('gulp-clean'),
    wiredep = require('wiredep').stream;

var config = {
    app:          'src/main/webapp/',
    bootstrapDir: 'bower_components/bootstrap',

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
            config.dist + 'index.html',
            config.work],
        {read: false})
        .pipe(clean());
});

gulp.task('copy', function() {
    return gulp.src(config.app + 'i18n/**')
        .pipe(gulp.dest(config.dist + 'i18n/'));
});

gulp.task('copy-fonts', function() {
    return gulp.src(config.bootstrapDir + '/fonts/**/*')
        .pipe(gulp.dest(config.dist + '/fonts'));
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
        .pipe(wiredep())
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

gulp.task('html', ['copy', 'copy-fonts', 'templates', 'preprocess'], function() {

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
        //.pipe(rev())
        .pipe(useref({
            searchPath: [config.work, config.app]
        }))
        .pipe(revReplace())
        .pipe(gulp.dest(config.dist));
});

gulp.task('build', ['html'], function() {
});

gulp.task('serve', ['preprocess'], function() {

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
