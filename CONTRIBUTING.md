[< Back](./README.md)

# Contributing to JDER Spring

Thanks for your interest in contributing!

This is a guideline for contributing to JDER Spring.

## Before the Contribution

Please install the following dependencies:

| Dependencies                                   | Description                            |
| ---------------------------------------------- | -------------------------------------- |
| [Kotlin](https://kotlinlang.org/)              | Programming language                   |
| [JDK](https://adoptium.net/temurin/releases)   | Java development kit                   |
| [Gradle](https://gradle.org/)                  | Build tool                             |
| [just](https://just.systems)                   | Command runner                         |
| [ls-lint](https://ls-lint.org/)                | Linting tool for directories and files |
| [typos-cli](https://github.com/crate-ci/typos) | Spell checker                          |

## Commands

The following commands are available:

### Default Command

This command will do linting, formatting and testing.

```sh
just
```

### Formatting

This command will format the code.

```sh
just fmt
```

### Linting

This command will lint the code.

```sh
just lint
```

### Building

This command will build the code.

```sh
just build
```

### Testing

This command will run all tests.

```sh
just test
```

### Cleaning

This command will clean the builds.

```sh
just clean
```

This command will clean all the unnecessary files.

```sh
just clean-all
```

## Committing

When committing changes to the code, use the following prefixes:

- `chore`: updates in dependencies/tools
- `build`: changes to the build system
- `fix`: fixes a bug
- `feat`: adds a new feature
- `refactor`: other code changes
- `perf`: performance improvements
- `security`: security related changes
- `style`: style changes
- `test`: adding or updating tests
- `docs`: documentation only changes
- `ci`: CI configuration updates
- `release`: new version release

For example:

```
feat: add xxx feature
docs: fix typos
```
