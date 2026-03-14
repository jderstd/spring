set shell := ["bash", "-cu"]
set windows-shell := ["pwsh", "-Command"]

# Default action
_:
    just check
    just build
    just test

# List commands
list:
    just --list

# Lint code with ls-lint
lslint:
    ls-lint -config ./.ls-lint.yaml

# Lint code with typos-cli
typos:
    typos

# Check code
check:
    just lslint
    just typos
    ktlint --format

# Build packages
build:
    gradle :spring:build

# Run tests
test:
    gradle :spring-test:test

# Publish packages
publish:
    gradle :spring:publishToMavenLocal

# Publish packages as dry-run
pusblish-try:
    gradle :spring:publishToMavenLocal --dry-run

# Clean
clean:
    gradle clean

# Clean everything (Linux)
clean-all-linux:
    just clean

    rm -rf ./.kotlin
    rm -rf ./.gradle

# Clean everything (macOS)
clean-all-macos:
    just clean-all-linux

# Clean everything (Windows)
clean-all-windows:
    just clean

    Remove-Item -Recurse -Force ./.kotlin
    Remove-Item -Recurse -Force ./.gradle

# Clean everything
clean-all:
    just clean-all-{{os()}}
