const React = require('react');

class Footer extends React.Component {
  docUrl(doc, language) {
    const baseUrl = this.props.config.baseUrl;
    return baseUrl + 'docs/' + doc;
  }

  render() {
    const currentYear = new Date().getFullYear();
    return (
      <footer className="nav-footer" id="footer">
        <section className="sitemap">
          <div>
            <h5>Sitemap</h5>
            <a href={this.docUrl('about.html', this.props.language)}>
              About
            </a>
            <a href={this.docUrl('server.index.html', this.props.language)}>
              Server
            </a>
            <a href={this.docUrl('ui.index.html', this.props.language)}>
              UI
            </a>
          </div>
          <div>
            <h5>Resources</h5>
            <a href={this.props.config.repoUrl}>Source</a>
          </div>
        </section>

        <section className="copyright">
          {this.props.config.copyright}
        </section>
      </footer>
    );
  }
}

module.exports = Footer;
