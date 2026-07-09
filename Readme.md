
# Distributed Library Reservation System

A Java console application simulating a real-world library reservation
system, built to practice designing a modular monolith with clean
boundaries between domains.

## Architecture

Five independent modules, each exposing a single public interface
(`I<Module>Service`) as its only external surface. No module accesses
another's repository or internal state directly — all cross-module
communication happens through these interfaces, which keeps the
dependency graph acyclic and each module independently testable.

- **User** — user registration and lookup
- **Catalogue** — book inventory and availability tracking
- **Reservation** — FIFO reservation queue per book
- **Borrowing** — borrow/return logic, coordinates Catalogue + Reservation
- **Notification** — console-based user notifications

## Problems this solves

- **Race conditions** — concurrent borrow attempts on the last available
  copy are serialized via synchronized service methods, preventing
  double-borrowing
- **Reservation queue ordering** — reservations are fulfilled strictly
  FIFO when a book is returned
- **Consistency trap** — a returned book is never marked publicly
  available while a reservation queue exists; it's handed directly to
  the next user in line
- **Queue skipping** — walk-up borrowing is blocked while an active
  reservation queue exists for a book

## Tech

Plain Java, no frameworks — in-memory storage, console I/O via `Scanner`.
