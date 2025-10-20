import { BiChevronLeft, BiChevronRight } from "react-icons/bi";

export default function Pagination({
  totalPages = 1,
  currentPage = 1,
  pageSize = 50,
  onPageChange,   // (page, pageSize)
  onPageSizeChange, // (newPageSize)
}) {
  const handleChangePage = (newPage) => {
    if (newPage < 1 || newPage > totalPages) return;
    onPageChange?.(newPage, pageSize);
  };

  const handlePageSizeChange = (newSize) => {
    onPageSizeChange?.(newSize);
    onPageChange?.(1, newSize); // reset về trang 1 khi đổi size
  };

  // Hàm tính các số trang hiển thị (dạng rút gọn)
  const getPages = () => {
    const pages = [];
    if (totalPages <= 7) {
      for (let i = 1; i <= totalPages; i++) pages.push(i);
    } else {
      pages.push(1);
      if (currentPage > 3) pages.push("...");
      if (currentPage > 2) pages.push(currentPage - 1);
      if (currentPage !== 1 && currentPage !== totalPages) pages.push(currentPage);
      if (currentPage < totalPages - 1) pages.push(currentPage + 1);
      if (currentPage < totalPages - 2) pages.push("...");
      pages.push(totalPages);
    }
    return pages;
  };

  return (
    <div className="flex items-center space-x-2 mt-4">
      {/* Prev */}
      <button
        onClick={() => handleChangePage(currentPage - 1)}
        className={`p-2 border rounded ${
          currentPage === 1 ? "text-gray-400 cursor-not-allowed" : "hover:bg-gray-100"
        }`}
        disabled={currentPage === 1}
      >
        <BiChevronLeft />
      </button>

      {/* Page numbers */}
      {getPages().map((p, idx) =>
        p === "..." ? (
          <span key={idx} className="px-2 py-1 text-gray-500">
            ...
          </span>
        ) : (
          <button
            key={idx}
            onClick={() => handleChangePage(p)}
            className={`px-3 py-1 border rounded ${
              p === currentPage
                ? "border-teal-500 text-teal-600"
                : "hover:bg-gray-100"
            }`}
          >
            {p}
          </button>
        )
      )}

      {/* Next */}
      <button
        onClick={() => handleChangePage(currentPage + 1)}
        className={`p-2 border rounded ${
          currentPage === totalPages
            ? "text-gray-400 cursor-not-allowed"
            : "hover:bg-gray-100"
        }`}
        disabled={currentPage === totalPages}
      >
        <BiChevronRight />
      </button>

      {/* Page size selector */}
      <select
        value={pageSize}
        onChange={(e) => handlePageSizeChange(Number(e.target.value))}
        className="ml-4 border rounded px-2 py-1"
      >
        {[10, 20, 50, 100].map((size) => (
          <option key={size} value={size}>
            {size} /Page
          </option>
        ))}
      </select>
    </div>
  );
}
