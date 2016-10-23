var path = require('path');
var webpack = require('webpack');

var SRC_DIR = path.resolve(__dirname, 'src')

module.exports = {
  devtool: 'eval',
  entry: [
    'webpack-dev-server/client?http://localhost:3000',
    'webpack/hot/only-dev-server',
    SRC_DIR + '/index.jsx'
  ],
  output: {
    path: path.join(__dirname, 'dist'),
    filename: 'bundle.js',
    publicPath: '/static/'
  },
  plugins: [
    new webpack.optimize.OccurenceOrderPlugin(),
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NoErrorsPlugin()
  ],
  cache: true,
  module: {
    loaders: [{
      test: /\.jsx?$/,
      loaders: ['react-hot', 'babel'],
      include: path.join(__dirname, 'src')
    }]
  },
  resolve: {
    root: SRC_DIR,
    components: SRC_DIR + '/components',
    services: SRC_DIR + '/services',
    views: SRC_DIR + '/views',
    reducers: SRC_DIR + '/reducers',
    extensions: ["", ".webpack.js", ".web.js", ".js", ".jsx"]
  }
};
