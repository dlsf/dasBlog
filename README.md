# dasBlog

The source code of the [blog.das.moe](https://blog.das.moe) website. Designed to be minimalistic, it serves mostly static Markdown files as slightly stylized HTML using [Javalin](https://github.com/javalin/javalin).

## Usage

Markdown files that should be published as posts need to be placed in the `posts/` directory and have to contain a specific YAML header. Sub-folders are being interpreted as categories.

Please refer to the example [about page](posts/about.md) for details.

## Running the site

You can host the machine locally via [docker-compose](docker-compose.yml).