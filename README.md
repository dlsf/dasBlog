# dasBlog

> [!IMPORTANT]
> [This repository has been moved to Codeberg](https://codeberg.org/das/dasBlog)

The source code of the [blog.das.moe](https://blog.das.moe) website. Designed to be minimalistic, it serves mostly static Markdown files as slightly stylized HTML using [Javalin](https://github.com/javalin/javalin).

## Usage

Markdown files that should be published as posts need to be placed in the `posts/` directory and need a specific YAML header. They are categorized via their `category` metadata, but additional folders can be used to structure them.

Please refer to the example [about page](posts/about.md) for details.

## Running the site

You can host the website locally via [docker-compose](docker-compose.yml).
